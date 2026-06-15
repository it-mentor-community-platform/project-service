package com.itmentorcommunityplatform.projectservice.service;

import com.itmentorcommunityplatform.projectservice.dto.CreateReviewViaFrontendRequest;
import com.itmentorcommunityplatform.projectservice.dto.ReviewResponse;
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
                request,
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
}