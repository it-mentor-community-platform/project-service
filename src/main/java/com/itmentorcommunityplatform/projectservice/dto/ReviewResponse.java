package com.itmentorcommunityplatform.projectservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

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