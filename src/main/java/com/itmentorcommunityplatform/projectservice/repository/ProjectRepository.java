package com.itmentorcommunityplatform.projectservice.repository;

import com.itmentorcommunityplatform.projectservice.model.Project;
import com.itmentorcommunityplatform.projectservice.model.RoadmapProject;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends ListCrudRepository<Project, Long> {
    Optional<Project> findByGithubRepositoryUrl(
            String githubRepositoryUrl
    );

    Optional<Project> findByIdAndAuthorTelegramUserId(
            Long id, Long authorTelegramUserId
    );

    List<Project> findAllByAuthorTelegramUserId(
            Long authorTelegramUserId
    );

    List<Project> findAllByAuthorTelegramUserIdAndRoadmapProjectOrderByAddedTimestampDesc(
            Long authorTelegramUserId,
            RoadmapProject roadmapProject
    );
}