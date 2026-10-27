package com.itmentorcommunityplatform.projectservice.repository;

import com.itmentorcommunityplatform.projectservice.model.Project;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends ListCrudRepository<Project, Long> {
    Optional<Project> findByGithubRepositoryUrl(String githubRepositoryUrl);

    List<Project> findAllByAuthorTelegramUserId(Long authorTelegramUserId);
}