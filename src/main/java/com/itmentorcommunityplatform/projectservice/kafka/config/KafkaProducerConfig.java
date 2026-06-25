package com.itmentorcommunityplatform.projectservice.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableKafka
public class KafkaProducerConfig {

    @Value("${kafka.topic.projects-project-created}")
    private String projectsProjectCreatedTopic;

    @Value("${kafka.topic.notifications-students-review-submitted}")
    private String reviewStudentNotificationTopic;

    @Bean
    public NewTopic projectsProjectCreatedTopic() {
        return TopicBuilder.name(projectsProjectCreatedTopic)
                .build();
    }

    @Bean
    public NewTopic reviewStudentNotificationTopic() {
        return TopicBuilder.name(reviewStudentNotificationTopic)
                .build();
    }

}
