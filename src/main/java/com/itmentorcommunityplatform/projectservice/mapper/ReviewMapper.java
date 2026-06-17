package com.itmentorcommunityplatform.projectservice.mapper;

import com.itmentorcommunityplatform.projectservice.dto.review.ReviewResponse;
import com.itmentorcommunityplatform.projectservice.kafka.ReviewStudentNotificationEvent;
import com.itmentorcommunityplatform.projectservice.model.Project;
import com.itmentorcommunityplatform.projectservice.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "reviewerTelegramUserId", source = "reviewerTelegramUserId")
    @Mapping(target = "url", source = "reviewUrl")
    @Mapping(target = "addedTimestamp", source = "addedTimestamp")
    Review toReviewEntity(
            String reviewUrl,
            Project project,
            Long reviewerTelegramUserId,
            Long addedTimestamp
    );

    @Mapping(target = "id", source = "review.id")
    @Mapping(target = "reviewerTelegramUserId", source = "review.reviewerTelegramUserId")
    @Mapping(target = "url", source = "review.url")
    @Mapping(target = "addedTimestamp", source = "review.addedTimestamp")
    @Mapping(target = "project", source = "project")
    ReviewResponse toReviewResponse(Review review, Project project);

    @Mapping(target = "id", source = "review.id")
    @Mapping(target = "reviewerTelegramUserId", source = "review.reviewerTelegramUserId")
    @Mapping(target = "url", source = "review.url")
    @Mapping(target = "addedTimestamp", source = "review.addedTimestamp")
    @Mapping(target = "project", source = "project")
    ReviewStudentNotificationEvent toEvent(Review review, Project project);

}
