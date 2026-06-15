CREATE TABLE IF NOT EXISTS reviews
(
    id                        BIGSERIAL PRIMARY KEY,
    project_id                BIGINT       NOT NULL,
    reviewer_telegram_user_id BIGINT       NOT NULL,
    url                       VARCHAR(255) NOT NULL,
    added_timestamp           BIGINT       NOT NULL,

    CONSTRAINT fk_reviews_project
        FOREIGN KEY (project_id)
            REFERENCES projects (id)
            ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_reviews_project_id
    ON reviews (project_id);

CREATE INDEX IF NOT EXISTS idx_reviews_reviewer_telegram_user_id
    ON reviews (reviewer_telegram_user_id);