package com.itmentorcommunityplatform.projectservice.dto.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itmentorcommunityplatform.projectservice.model.RoadmapProject;

public record ProjectResponse(
        Long id,

        @JsonProperty("author_telegram_user_id")
        Long authorTelegramUserId,

        @JsonProperty("github_repository_url")
        String githubRepositoryUrl,

        @JsonProperty("programming_language")
        String programmingLanguage,

        @JsonProperty("roadmap_project")
        RoadmapProject roadmapProject,

        @JsonProperty("added_timestamp")
        Long addedTimestamp
) {
}