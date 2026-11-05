package com.itmentorcommunityplatform.projectservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("projects")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Project {
        @Id
        private Long id;
        private Long authorTelegramUserId;
        private String githubRepositoryUrl;
        private String programmingLanguage;
        private RoadmapProject roadmapProject;
        private Long addedTimestamp;
        private String deploymentUrl;
}