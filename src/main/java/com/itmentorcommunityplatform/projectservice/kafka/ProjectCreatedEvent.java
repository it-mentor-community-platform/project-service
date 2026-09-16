package com.itmentorcommunityplatform.projectservice.kafka;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.itmentorcommunityplatform.projectservice.model.DataSourceType;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProjectCreatedEvent(Long authorTelegramUserId,
                                  String authorTelegramProfileUrl,
                                  String githubRepositoryUrl,
                                  String programmingLanguage,
                                  String roadmapProject,
                                  Long addedTimestamp,
                                  DataSourceType projectSourceType) {
}
