package com.itmentorcommunityplatform.projectservice.controller;

import com.itmentorcommunityplatform.projectservice.docs.project.CreateProjectViaFrontendDocs;
import com.itmentorcommunityplatform.projectservice.docs.project.CreateProjectViaTelegramBotOrImporterDocs;
import com.itmentorcommunityplatform.projectservice.dto.project.CreateProjectViaFrontendRequest;
import com.itmentorcommunityplatform.projectservice.dto.project.CreateProjectViaTelegramBotOrImportRequest;
import com.itmentorcommunityplatform.projectservice.dto.project.ProjectResponse;
import com.itmentorcommunityplatform.projectservice.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;


    @PostMapping("/project")
    @CreateProjectViaFrontendDocs
    public ResponseEntity<ProjectResponse> createProjectViaFrontend(
            @RequestHeader("X-Telegram-User-Id") Long telegramUserId,
            @RequestHeader(value = "X-Telegram-Username", required = false) String username,
            @Valid @RequestBody CreateProjectViaFrontendRequest request
    ) {
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
}
