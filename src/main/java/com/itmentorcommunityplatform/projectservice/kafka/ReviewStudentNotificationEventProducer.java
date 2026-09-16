package com.itmentorcommunityplatform.projectservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewStudentNotificationEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.notifications-students-review-submitted}")
    private String reviewStudentNotificationTopic;

    public void sendReviewStudentNotification(ReviewStudentNotificationEvent event) {
        kafkaTemplate.send(reviewStudentNotificationTopic, event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Event sent to partition {}",
                                result.getRecordMetadata().partition());
                    } else {
                        log.error("Failed to send event", ex);
                    }
                });
    }
}