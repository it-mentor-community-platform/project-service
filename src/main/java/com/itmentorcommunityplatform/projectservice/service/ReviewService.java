package com.itmentorcommunityplatform.projectservice.service;

import com.itmentorcommunityplatform.projectservice.dto.review.CreateReviewViaFrontendRequest;
import com.itmentorcommunityplatform.projectservice.dto.review.CreateReviewViaImporterRequest;
import com.itmentorcommunityplatform.projectservice.dto.review.ReviewResponse;
import com.itmentorcommunityplatform.projectservice.kafka.ReviewStudentNotificationEventProducer;
import com.itmentorcommunityplatform.projectservice.mapper.ReviewMapper;
import com.itmentorcommunityplatform.projectservice.model.Project;
import com.itmentorcommunityplatform.projectservice.model.Review;
import com.itmentorcommunityplatform.projectservice.repository.ProjectRepository;
import com.itmentorcommunityplatform.projectservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ProjectRepository projectRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final ReviewStudentNotificationEventProducer reviewStudentNotificationProducer;

    @Transactional
    public ReviewResponse createReviewViaFrontend(
            CreateReviewViaFrontendRequest request,
            Long reviewerTelegramUserId
    ) {
        log.info(
                "Creating review via frontend: projectGithubRepositoryUrl={}, reviewerTelegramUserId={}",
                request.projectGithubRepositoryUrl(),
                reviewerTelegramUserId
        );

        Project project = projectRepository.findByGithubRepositoryUrl(request.projectGithubRepositoryUrl())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Project not found"
                ));

        Long addedTimestamp = System.currentTimeMillis() / 1000;

        Review review = reviewMapper.toReviewEntity(
                request.reviewUrl(),
                project,
                reviewerTelegramUserId,
                addedTimestamp
        );

        Review savedReview = reviewRepository.save(review);

        log.info(
                "Review created via frontend: reviewId={}, projectId={}, reviewerTelegramUserId={}",
                savedReview.getId(),
                project.getId(),
                reviewerTelegramUserId
        );

        reviewStudentNotificationProducer.sendReviewStudentNotification(reviewMapper.toEvent(savedReview, project));

        return reviewMapper.toReviewResponse(savedReview, project);
    }

    @Transactional
    public ReviewResponse createReviewViaImporter(CreateReviewViaImporterRequest request) {
        String projectGithubRepositoryUrl = request.projectGithubRepositoryUrl();
        String reviewUrl = request.reviewUrl();
        long reviewerTelegramUserId = request.reviewerTelegramUserId();
        long addedTimestamp = request.addedTimestamp() != null
                ? request.addedTimestamp()
                : Instant.now().getEpochSecond();

        log.info(
                "Creating review via Data Importer: projectGithubRepositoryUrl={}, reviewerTelegramUserId={}",
                projectGithubRepositoryUrl,
                reviewerTelegramUserId
        );

        Project project = projectRepository.findByGithubRepositoryUrl(projectGithubRepositoryUrl)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Project not found"
                ));

        Review review = reviewMapper.toReviewEntity(
                reviewUrl,
                project,
                reviewerTelegramUserId,
                addedTimestamp
        );

        Review savedReview = reviewRepository.save(review);

        log.info(
                "Review created via Data Importer: reviewId={}, projectId={}, reviewerTelegramUserId={}",
                savedReview.getId(),
                project.getId(),
                reviewerTelegramUserId
        );

        reviewStudentNotificationProducer.sendReviewStudentNotification(reviewMapper.toEvent(savedReview, project));

        return reviewMapper.toReviewResponse(savedReview, project);
    }

}