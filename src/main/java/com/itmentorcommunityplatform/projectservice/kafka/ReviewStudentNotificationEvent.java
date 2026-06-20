package com.itmentorcommunityplatform.projectservice.kafka;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.itmentorcommunityplatform.projectservice.dto.project.ProjectResponse;
import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ReviewStudentNotificationEvent(Long id,
                                             Long reviewerTelegramUserId,
                                             String url,
                                             Long addedTimestamp,
                                             ProjectResponse project) {
}
