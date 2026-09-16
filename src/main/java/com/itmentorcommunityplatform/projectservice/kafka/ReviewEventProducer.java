package com.itmentorcommunityplatform.projectservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.reviews-review-created}")
    private String reviewsReviewCreatedTopic;

    public void sendProjectCreated(ReviewCreatedEvent event) {
        kafkaTemplate.send(reviewsReviewCreatedTopic, event)
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
