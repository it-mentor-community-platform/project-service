package com.itmentorcommunityplatform.projectservice.service;

import com.itmentorcommunityplatform.projectservice.dto.request.project.CreateProjectViaFrontendRequest;
import com.itmentorcommunityplatform.projectservice.dto.request.project.CreateProjectViaTelegramBotOrImportRequest;
import com.itmentorcommunityplatform.projectservice.dto.response.ProjectResponse;
import com.itmentorcommunityplatform.projectservice.dto.response.ProjectsSummaryResponse;
import com.itmentorcommunityplatform.projectservice.exception.AccessDeniedException;
import com.itmentorcommunityplatform.projectservice.kafka.producer.ProjectEventProducer;
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
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

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

    private static final String ROLE_STUDENT = "STUDENT";


    @Transactional
    public ProjectsSummaryResponse getProjectsSummaryToUserId(Long authorId, String userRoles) {

        if (!isStudent(userRoles)) {
            log.warn("User is not a student");
            throw new AccessDeniedException("User does not have STUDENT role");
        }

        var projects = projectRepository.findAllByAuthorTelegramUserId(authorId);
        log.info("Found {} projects for authorId={}", projects.size(), authorId);

        return new ProjectsSummaryResponse(projects.stream()
                .collect(Collectors.groupingBy(
                        Project::getRoadmapProject,
                        LinkedHashMap::new,
                        Collectors.mapping(
                                Project::getProgrammingLanguage,
                                Collectors.toList()
                        )
                )));
    }

    private boolean isStudent(String userRoles) {
        String[] roles = userRoles.toUpperCase().split(",");
        return Arrays.asList(roles).contains(ROLE_STUDENT);
    }

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
