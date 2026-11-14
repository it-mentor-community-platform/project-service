package com.itmentorcommunityplatform.projectservice.kafka.producer;

import com.itmentorcommunityplatform.projectservice.kafka.ProjectCreatedEvent;
import com.itmentorcommunityplatform.projectservice.kafka.ProjectUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.projects-project-created}")
    private String projectsProjectCreatedTopic;

    @Value("${kafka.topic.projects-project-updated}")
    private String projectsProjectUpdatedTopic;

    public void sendProjectCreated(ProjectCreatedEvent event) {
        kafkaTemplate.send(projectsProjectCreatedTopic, event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("ProjectCreatedEvent sent to partition {}",
                                result.getRecordMetadata().partition());
                    } else {
                        log.error("Failed to send event", ex);
                    }
                });
    }

    public void sendProjectUpdated(ProjectUpdatedEvent event) {
        kafkaTemplate.send(projectsProjectUpdatedTopic, event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("ProjectUpdatedEvent sent to partition {}",
                                result.getRecordMetadata().partition());
                    } else {
                        log.error("Failed to send event", ex);
                    }
                });
    }
}
