package com.itmentorcommunityplatform.projectservice.kafka;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.itmentorcommunityplatform.projectservice.dto.project.ProjectResponse;
import com.itmentorcommunityplatform.projectservice.model.DataSourceType;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ReviewCreatedEvent(

        Long id,
        Long reviewerTelegramUserId,
        String reviewerTelegramProfileUrl,
        String url,
        Long addedTimestamp,

        ProjectResponse project,
        DataSourceType reviewSourceType
) {
}
