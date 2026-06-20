package com.itmentorcommunityplatform.projectservice.dto.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itmentorcommunityplatform.projectservice.dto.project.ProjectResponse;

public record ReviewResponse(
        Long id,

        @JsonProperty("reviewer_telegram_user_id")
        Long reviewerTelegramUserId,

        String url,

        @JsonProperty("added_timestamp")
        Long addedTimestamp,

        ProjectResponse project
) {
}