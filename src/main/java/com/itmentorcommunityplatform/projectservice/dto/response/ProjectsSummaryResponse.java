package com.itmentorcommunityplatform.projectservice.dto.response;

import com.fasterxml.jackson.annotation.JsonValue;
import com.itmentorcommunityplatform.projectservice.model.RoadmapProject;

import java.util.List;
import java.util.Map;

public record ProjectsSummaryResponse(
        @JsonValue
        Map<RoadmapProject, List<String>> summary
) {
}
