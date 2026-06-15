package com.itmentorcommunityplatform.projectservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("reviews")
public class Review {

    @Id
    private Long id;

    @Column("project_id")
    private Long projectId;

    @Column("reviewer_telegram_user_id")
    private Long reviewerTelegramUserId;

    @Column("url")
    private String url;

    @Column("added_timestamp")
    private Long addedTimestamp;
}