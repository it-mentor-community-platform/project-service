package com.itmentorcommunityplatform.projectservice.dto.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itmentorcommunityplatform.projectservice.model.DataSourceType;
import com.itmentorcommunityplatform.projectservice.model.RoadmapProject;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record CreateProjectViaTelegramBotOrImportRequest(
        @NotNull
        @JsonProperty("author_telegram_user_id")
        Long authorTelegramUserId,

        @NotBlank(message = "GitHub repository URL cannot be empty")
        @URL(
                protocol = "https",
                host = "github.com",
                message = "URL must be a valid GitHub https URL"
        )
        @JsonProperty("github_repository_url")
        String githubRepositoryUrl,

        @NotBlank
        @JsonProperty("programming_language")
        String programmingLanguage,

        @NotNull
        @JsonProperty("roadmap_project")
        RoadmapProject roadmapProject,

        @Nullable
        @JsonProperty("author_telegram_username")
        String telegramUsername,

        @JsonProperty("added_timestamp")
        Long addedTimestamp,

        @NotNull
        @JsonProperty("project_source_type")
        DataSourceType dataSourceType
) {
}
