package com.itmentorcommunityplatform.projectservice.exception;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(String projectUrl) {
        super(projectUrl);
    }
}
