package com.itmentorcommunityplatform.projectservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum RoadmapProject {
    HANGMAN,
    SIMULATION,
    CURRENCY_EXCHANGE,
    TENNIS_SCOREBOARD,
    WEATHER_VIEWER,
    CLOUD_FILE_STORAGE,
    TASK_TRACKER,
    OTHER;


    @JsonCreator
    public static RoadmapProject from(String value) {
        if (value == null) {
            return null;
        }

        String normalized = value.replace("-", "_");

        for (RoadmapProject type : values()) {
            if (type.name().equalsIgnoreCase(normalized)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Unknown RoadmapProjectType: " + value);
    }

}