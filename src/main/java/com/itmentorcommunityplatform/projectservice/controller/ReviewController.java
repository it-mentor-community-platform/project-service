package com.itmentorcommunityplatform.projectservice.controller;

import com.itmentorcommunityplatform.projectservice.dto.CreateReviewViaFrontendRequest;
import com.itmentorcommunityplatform.projectservice.dto.ReviewResponse;
import com.itmentorcommunityplatform.projectservice.service.ReviewService;
import com.itmentorcommunityplatform.projectservice.docs.review.CreateReviewViaFrontendDocs;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
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
}