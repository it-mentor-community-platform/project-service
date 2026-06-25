package com.itmentorcommunityplatform.projectservice.dto.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itmentorcommunityplatform.projectservice.model.RoadmapProject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record CreateProjectViaFrontendRequest(
        @NotBlank(message = "GitHub repository URL cannot be empty")
        @URL(
                protocol = "https",
                host = "github.com",
                message = "URL must be a valid GitHub https URL"
        )
        @JsonProperty("github_repository_url")
        String githubRepositoryUrl,

        @NotBlank(message = "Programming language cannot be empty")
        @JsonProperty("programming_language")
        String programmingLanguage,

        @NotNull(message = "Roadmap project cannot be empty")
        @JsonProperty("roadmap_project")
        RoadmapProject roadmapProject,

        @JsonProperty("added_timestamp")
        Long addedTimestamp,

        @JsonProperty("project_source_type")
        String projectSourceType
) {
}