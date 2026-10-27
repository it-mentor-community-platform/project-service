package com.itmentorcommunityplatform.projectservice.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.itmentorcommunityplatform.projectservice.model.RoadmapProject;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)

public record ProjectResponse(
        Long id,
        Long authorTelegramUserId,
        String githubRepositoryUrl,
        String programmingLanguage,
        RoadmapProject roadmapProject,
        Long addedTimestamp
) {
}