package com.itmentorcommunityplatform.projectservice.dto.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record CreateReviewViaImporterRequest(
        @NotBlank(message = "Project GitHub repository URL cannot be empty")
        @URL(
                protocol = "https",
                host = "github.com",
                message = "URL must be a valid GitHub https URL"
        )
        @JsonProperty("project_github_repository_url")
        String projectGithubRepositoryUrl,

        @NotBlank(message = "Review URL cannot be empty")
        @URL(
                protocol = "https",
                message = "Review URL must be a valid https URL"
        )
        @JsonProperty("review_url")
        String reviewUrl,

        @NotNull(message = "Review telegram user id cannot be empty")
        @JsonProperty("reviewer_telegram_user_id")
        Long reviewerTelegramUserId,

        @JsonProperty("added_timestamp")
        Long addedTimestamp
) {
}