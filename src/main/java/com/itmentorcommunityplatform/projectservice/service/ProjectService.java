package com.itmentorcommunityplatform.projectservice.service;

import com.itmentorcommunityplatform.projectservice.dto.project.CreateProjectViaFrontendRequest;
import com.itmentorcommunityplatform.projectservice.dto.project.CreateProjectViaTelegramBotOrImportRequest;
import com.itmentorcommunityplatform.projectservice.dto.project.ProjectResponse;
import com.itmentorcommunityplatform.projectservice.kafka.ProjectEventProducer;
import com.itmentorcommunityplatform.projectservice.mapper.ProjectMapper;
import com.itmentorcommunityplatform.projectservice.model.DataSourceType;
import com.itmentorcommunityplatform.projectservice.model.Project;
import com.itmentorcommunityplatform.projectservice.repository.ProjectRepository;
import io.micrometer.core.instrument.Counter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectEventProducer projectEventProducer;
    private final ProjectMapper projectMapper;

    private final Counter projectsCreatedViaFrontendCounter;
    private final Counter projectsCreatedViaTelegramBotCounter;
    private final Counter projectsCreatedViaImporterCounter;


    @Transactional
    public ProjectResponse createProjectViaFrontend(
            Long authorId,
            String username,
            CreateProjectViaFrontendRequest request
    ) {
        long timestamp = Instant.now().getEpochSecond();

        log.info("Creating project via FRONTEND. authorId={}, username={}, timestamp={}",
                authorId, username, timestamp);

        Project project = projectMapper.toEntity(request, authorId, timestamp);
        projectRepository.save(project);
        log.info("Project created via FRONTEND. projectId={}", project.getId());

        projectsCreatedViaFrontendCounter.increment();

        String telegramProfileUrl = buildTelegramProfileUrl(username);
        projectEventProducer.sendProjectCreated(
                projectMapper.toEvent(project, telegramProfileUrl, DataSourceType.FRONTEND)
        );

        return projectMapper.toResponse(project);
    }

    @Transactional
    public ProjectResponse createProjectViaTelegramBotOrImporter(
            CreateProjectViaTelegramBotOrImportRequest request
    ) {
        Long requestedTimestamp = request.addedTimestamp();
        long addedTimestamp = request.dataSourceType() == DataSourceType.DATA_IMPORTER
                && requestedTimestamp != null
                ? request.addedTimestamp()
                : Instant.now().getEpochSecond();

        Project project = projectMapper.toEntity(request, addedTimestamp);
        projectRepository.save(project);
        log.info("Project saved via {}. projectId={}",
                request.dataSourceType(), project.getId());

        incrementProjectMetric(request.dataSourceType());

        String telegramProfileUrl = buildTelegramProfileUrl(request.telegramUsername());
        projectEventProducer.sendProjectCreated(
                projectMapper.toEvent(project, telegramProfileUrl, request.dataSourceType())
        );
        return projectMapper.toResponse(project);
    }

    private void incrementProjectMetric(DataSourceType sourceType) {
        switch (sourceType) {
            case TELEGRAM_BOT -> projectsCreatedViaTelegramBotCounter.increment();
            case DATA_IMPORTER -> projectsCreatedViaImporterCounter.increment();
        }
    }

    private String buildTelegramProfileUrl(String username) {
        return "https://t.me/" + username;
    }
}
