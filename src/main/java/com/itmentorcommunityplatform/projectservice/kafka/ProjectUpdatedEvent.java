package com.itmentorcommunityplatform.projectservice.kafka;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProjectUpdatedEvent(Long authorTelegramUserId,
                                  String githubRepositoryUrl,
                                  String deploymentUrl,
                                  String previousGithubRepositoryUrl,
                                  Long addedTimestamp) {
}
