ALTER TABLE projects
    ADD COLUMN deployment_url VARCHAR(255),
    ADD CONSTRAINT chk_projects_deployment_url
        CHECK (roadmap_project IN
    ('CURRENCY_EXCHANGE', 'TENNIS_SCOREBOARD', 'WEATHER_VIEWER', 'CLOUD_FILE_STORAGE', 'TASK_TRACKER')
    OR deployment_url IS NULL)