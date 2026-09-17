package com.itmentorcommunityplatform.projectservice.controller;

import com.itmentorcommunityplatform.projectservice.docs.review.CreateReviewViaFrontendDocs;
import com.itmentorcommunityplatform.projectservice.docs.review.CreateReviewViaImporter;
import com.itmentorcommunityplatform.projectservice.dto.request.review.CreateReviewViaFrontendRequest;
import com.itmentorcommunityplatform.projectservice.dto.request.review.CreateReviewViaImporterRequest;
import com.itmentorcommunityplatform.projectservice.dto.response.ReviewResponse;
import com.itmentorcommunityplatform.projectservice.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/review")
    @CreateReviewViaFrontendDocs
    public ResponseEntity<ReviewResponse> createReviewViaFrontend(
            @RequestHeader("X-Telegram-User-Id") Long reviewerTelegramUserId,
            @RequestHeader("X-Telegram-Username") String reviewerTelegramUsername,
            @Valid @RequestBody CreateReviewViaFrontendRequest request
    ) {
        ReviewResponse response = reviewService.createReviewViaFrontend(
                request,
                reviewerTelegramUserId,
                reviewerTelegramUsername
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/internal/review")
    @CreateReviewViaImporter
    public ResponseEntity<ReviewResponse> createProjectViaImporter(
            @RequestHeader("X-Telegram-Username") String reviewerTelegramUsername,
            @Valid @RequestBody CreateReviewViaImporterRequest request
    ) {
        ReviewResponse response = reviewService.createReviewViaImporter(request, reviewerTelegramUsername);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}