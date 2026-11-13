package com.itmentorcommunityplatform.projectservice.controller;

import com.itmentorcommunityplatform.projectservice.docs.project.*;
import com.itmentorcommunityplatform.projectservice.dto.request.project.CreateProjectViaFrontendRequest;
import com.itmentorcommunityplatform.projectservice.dto.request.project.CreateProjectViaTelegramBotOrImportRequest;
import com.itmentorcommunityplatform.projectservice.dto.request.project.UpdateProjectViaFrontendRequest;
import com.itmentorcommunityplatform.projectservice.dto.response.ProjectResponse;
import com.itmentorcommunityplatform.projectservice.dto.response.ProjectsSummaryResponse;
import com.itmentorcommunityplatform.projectservice.exception.AccessDeniedException;
import com.itmentorcommunityplatform.projectservice.model.RoadmapProject;
import com.itmentorcommunityplatform.projectservice.service.ProjectService;
import com.itmentorcommunityplatform.projectservice.validation.ProjectValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project")
@Validated
@RequiredArgsConstructor
@Slf4j
public class ProjectController {

    private final ProjectService projectService;

    private static final String ROLE_STUDENT = "STUDENT";


    @GetMapping("/projects/my/summary")
    @GetProjectsSummaryDocs
    public ResponseEntity<ProjectsSummaryResponse> getProjectsToSummary(
            @RequestHeader("X-Telegram-User-Id") Long telegramUserId,
            @RequestHeader("X-User-Roles") List<String> userRoles
    ) {
        checkForPresenceOfStudentUserRole(userRoles);
        ProjectsSummaryResponse response = projectService.getProjectsSummaryToUserId(telegramUserId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/projects/my")
    @GetProjectImplementationsDocs
    public ResponseEntity<List<ProjectResponse>> getProjectImplementationsByProjectType(
            @RequestHeader("X-Telegram-User-Id") Long telegramUserId,
            @RequestHeader("X-User-Roles") List<String> userRoles,
            @RequestParam(value = "roadmap_project") RoadmapProject roadmapProject
    ) {
        checkForPresenceOfStudentUserRole(userRoles);
        List<ProjectResponse> response = projectService.getProjectImplementations(telegramUserId, roadmapProject);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/project")
    @CreateProjectViaFrontendDocs
    public ResponseEntity<ProjectResponse> createProjectViaFrontend(
            @RequestHeader("X-Telegram-User-Id") Long telegramUserId,
            @RequestHeader(value = "X-Telegram-Username", required = false) String username,
            @Valid @RequestBody CreateProjectViaFrontendRequest request
    ) {
        ProjectValidator.validateDeploymentUrl(request.deploymentUrl(), request.roadmapProject());
        ProjectResponse response = projectService.createProjectViaFrontend(telegramUserId, username, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/internal/project")
    @CreateProjectViaTelegramBotOrImporterDocs
    public ResponseEntity<ProjectResponse> createProjectViaTelegramBotOrImporter(
            @Valid @RequestBody CreateProjectViaTelegramBotOrImportRequest request
    ) {
        ProjectResponse response = projectService.createProjectViaTelegramBotOrImporter(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/projects/{id}")
    @UpdateProjectViaFrontendDocs
    public ResponseEntity<ProjectResponse> updateProjectViaFrontend(
            @RequestHeader("X-Telegram-User-Id") Long telegramUserId,
            @RequestHeader("X-User-Roles") List<String> userRoles,
            @PathVariable("id") Long id,
            @Valid @RequestBody UpdateProjectViaFrontendRequest request
    ) {
      checkForPresenceOfStudentUserRole(userRoles);
      ProjectValidator.validatePatchProjectRequest(request);
      ProjectResponse response = projectService.updateProjectViaFrontend(id, telegramUserId, request);
      return ResponseEntity.status(HttpStatus.OK).body(response);
    }
      
    @GetMapping("/projects/{id}")
    @GetProjectImplementationByIdDocs
    public ResponseEntity<ProjectResponse> getProjectImplementationById(
            @RequestHeader("X-Telegram-User-Id") Long telegramUserId,
            @RequestHeader("X-User-Roles") List<String> userRoles,
            @Positive(message = "Project implementation ID must be positive")
            @PathVariable("id") Long projectId
    ) {
        checkForPresenceOfStudentUserRole(userRoles);
        ProjectResponse response = projectService.getProjectImplementationByIdAndUserId(projectId, telegramUserId);
        return ResponseEntity.ok(response);
    }

    private void checkForPresenceOfStudentUserRole(List<String> userRoles) {
        if (!userRoles.contains(ROLE_STUDENT)) {
            log.warn("User is not a student");
            throw new AccessDeniedException("User does not have STUDENT role");
        }
    }
}
