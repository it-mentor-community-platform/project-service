package com.itmentorcommunityplatform.projectservice.kafka;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProjectUpdatedEvent(
        Long id,
        Long authorTelegramUserId,
        String githubRepositoryUrl,
        String previousGithubRepositoryUrl,
        String programmingLanguage,
        String roadmapProject,
        Long addedTimestamp,
        String deploymentUrl
) {
}