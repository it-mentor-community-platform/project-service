package com.itmentorcommunityplatform.projectservice.dto.request.project;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.http.converter.HttpMessageNotReadableException;

public record UpdateProjectViaFrontendRequest(
        @JsonProperty("github_repository_url")
        @URL(
                protocol = "https",
                host = "github.com",
                message = "URL must be a valid GitHub https URL"
        )
        @NotBlank(message = "Github repository url cannot be empty")
        String githubRepositoryUrl,

        @JsonProperty("deployment_url")
        @URL(
                protocol = "https",
                message = "URL must be a valid https URL"
        )
        String deploymentUrl
) {
        @JsonAnySetter
        public void handleUnknown(String field, Object value) {
                throw new HttpMessageNotReadableException("Unrecognized field: " + field);
        }
}