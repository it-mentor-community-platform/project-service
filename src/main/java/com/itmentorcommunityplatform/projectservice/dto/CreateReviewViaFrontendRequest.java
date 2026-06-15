package com.itmentorcommunityplatform.projectservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record CreateReviewViaFrontendRequest(
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
        String reviewUrl
) {
}