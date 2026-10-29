package com.itmentorcommunityplatform.projectservice.service;

import com.itmentorcommunityplatform.projectservice.dto.request.project.CreateProjectViaFrontendRequest;
import com.itmentorcommunityplatform.projectservice.dto.request.project.CreateProjectViaTelegramBotOrImportRequest;
import com.itmentorcommunityplatform.projectservice.dto.response.ProjectResponse;
import com.itmentorcommunityplatform.projectservice.dto.response.ProjectsSummaryResponse;
import com.itmentorcommunityplatform.projectservice.kafka.producer.ProjectEventProducer;
import com.itmentorcommunityplatform.projectservice.mapper.ProjectMapper;
import com.itmentorcommunityplatform.projectservice.model.DataSourceType;
import com.itmentorcommunityplatform.projectservice.model.Project;
import com.itmentorcommunityplatform.projectservice.model.RoadmapProject;
import com.itmentorcommunityplatform.projectservice.repository.ProjectRepository;
import io.micrometer.core.instrument.Counter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
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


    @Transactional
    public ProjectsSummaryResponse getProjectsSummaryToUserId(Long authorId) {

        var projects = projectRepository.findAllByAuthorTelegramUserId(authorId);
        log.info("Found {} projects for authorId={}", projects.size(), authorId);

        return new ProjectsSummaryResponse(projects.stream()
                .sorted(Comparator.comparing(Project::getAddedTimestamp))
                .collect(Collectors.groupingBy(
                        Project::getRoadmapProject,
                        LinkedHashMap::new,
                        Collectors.mapping(
                                Project::getProgrammingLanguage,
                                Collectors.collectingAndThen(
                                        Collectors.toCollection(LinkedHashSet::new),
                                        List::copyOf
                                )
                        )
                )));
    }

    public List<ProjectResponse> getProjectImplementations(
            Long authorId,
            RoadmapProject project
    ) {
        List<Project> projects
                = projectRepository
                .findAllByAuthorTelegramUserIdAndRoadmapProjectOrderByAddedTimestampDesc(authorId, project);
        log.info("Found {} concrete projects of {} for authorId={}", projects.size(), project, authorId);

        return projectMapper.toResponseList(projects);
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
