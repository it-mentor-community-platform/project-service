package com.itmentorcommunityplatform.projectservice.validation;

import com.itmentorcommunityplatform.projectservice.dto.request.project.UpdateProjectViaFrontendRequest;
import com.itmentorcommunityplatform.projectservice.exception.InvalidInputException;
import com.itmentorcommunityplatform.projectservice.model.RoadmapProject;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class ProjectValidator {
    public static void validateDeploymentUrl(String deploymentUrl, RoadmapProject roadmapProject) {
        if (deploymentUrl == null) {
            return;
        }
        if (!roadmapProject.supportsDeployment()) {
            throw new InvalidInputException("The project does not support deployment");
        }
    }

    public static void validatePatchProjectRequest(UpdateProjectViaFrontendRequest request) {
        String deploymentUrl = request.deploymentUrl();

        if (deploymentUrl == null) {
            return;
        }
        if (deploymentUrl.isBlank()) {
            throw new InvalidInputException("Deployment URL cannot be empty");
        }
    }
}