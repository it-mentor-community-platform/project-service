package com.itmentorcommunityplatform.projectservice.mapper;

import com.itmentorcommunityplatform.projectservice.dto.project.CreateProjectViaFrontendRequest;
import com.itmentorcommunityplatform.projectservice.dto.project.CreateProjectViaTelegramBotOrImportRequest;
import com.itmentorcommunityplatform.projectservice.dto.project.ProjectResponse;
import com.itmentorcommunityplatform.projectservice.kafka.ProjectCreatedEvent;
import com.itmentorcommunityplatform.projectservice.model.DataSourceType;
import com.itmentorcommunityplatform.projectservice.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(target = "authorTelegramProfileUrl", source = "telegramProfileUrl")
    @Mapping(target = "projectSourceType", source = "sourceType")
    ProjectCreatedEvent toEvent(Project project, String telegramProfileUrl, DataSourceType sourceType);

    ProjectResponse toResponse(Project project);

    @Mapping(target = "addedTimestamp", source = "addedTimestamp")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorTelegramUserId", source = "authorTelegramUserId")
    Project toEntity(CreateProjectViaFrontendRequest frontendDto, Long authorTelegramUserId, Long addedTimestamp);

    @Mapping(target = "addedTimestamp", source = "addedTimestamp")
    Project toEntity(CreateProjectViaTelegramBotOrImportRequest telegramBotOrImportRequestDto, Long addedTimestamp);
}
