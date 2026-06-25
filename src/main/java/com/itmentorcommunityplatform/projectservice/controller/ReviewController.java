package com.itmentorcommunityplatform.projectservice.controller;

import com.itmentorcommunityplatform.projectservice.docs.review.CreateReviewViaImporter;
import com.itmentorcommunityplatform.projectservice.dto.review.CreateReviewViaFrontendRequest;
import com.itmentorcommunityplatform.projectservice.dto.review.CreateReviewViaImporterRequest;
import com.itmentorcommunityplatform.projectservice.dto.review.ReviewResponse;
import com.itmentorcommunityplatform.projectservice.service.ReviewService;
import com.itmentorcommunityplatform.projectservice.docs.review.CreateReviewViaFrontendDocs;
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
            @Valid @RequestBody CreateReviewViaFrontendRequest request
    ) {
        ReviewResponse response = reviewService.createReviewViaFrontend(
                request,
                reviewerTelegramUserId
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/internal/review")
    @CreateReviewViaImporter
    public ResponseEntity<ReviewResponse> createProjectViaImporter(
            @Valid @RequestBody CreateReviewViaImporterRequest request
    ) {
        ReviewResponse response = reviewService.createReviewViaImporter(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}