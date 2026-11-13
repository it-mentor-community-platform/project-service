package com.itmentorcommunityplatform.projectservice.docs.project;

import com.itmentorcommunityplatform.projectservice.dto.ErrorResponse;
import com.itmentorcommunityplatform.projectservice.dto.response.ProjectResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "get personal project implementation by id",
        description = """
                Returns one project implementation of the current user by project ID.
                
                Required headers:
                - `X-Telegram-User-Id` — Telegram user ID
                - `X-User-Roles` — user roles (access allowed only for STUDENT)
                
                Required path parameter:
                - `id` — project implementation ID (must be a positive number)
                """,
        parameters = {
                @Parameter(
                        name = "X-Telegram-User-Id",
                        in = ParameterIn.HEADER,
                        required = true,
                        description = "Telegram user ID",
                        example = "123456789"
                ),
                @Parameter(
                        name = "X-User-Roles",
                        in = ParameterIn.HEADER,
                        required = true,
                        description = "User roles. Access is allowed only for STUDENT",
                        example = "STUDENT"
                ),
                @Parameter(
                        name = "id",
                        in = ParameterIn.PATH,
                        required = true,
                        description = "Personal project implementation ID. Must be a positive number",
                        example = "123"
                )
        }
)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Personal project implementation successfully returned",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ProjectResponse.class),
                        examples = {
                                @ExampleObject(
                                        name = "Project implementation",
                                        value = """
                                                {
                                                  "id": 123,
                                                  "author_telegram_user_id": 1,
                                                  "github_repository_url": "https://github.com/example-user/simulation",
                                                  "programming_language": "Java",
                                                  "roadmap_project": "SIMULATION",
                                                  "added_timestamp": 123456,
                                                  "deployment_url": null
                                                }
                                                """
                                )
                        }
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Missing required header or invalid input",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class),
                        examples = {
                                @ExampleObject(
                                        name = "Missing X-Telegram-User-Id",
                                        value = """
                                                {
                                                  "message": "Missing required header: X-Telegram-User-Id"
                                                }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Invalid id",
                                        value = """
                                                {
                                                  "message": "Invalid value for parameter: id"
                                                }
                                                """
                                )
                        }
                )
        ),
        @ApiResponse(
                responseCode = "403",
                description = "User does not have STUDENT role",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class),
                        examples = @ExampleObject(
                                value = """
                                        {
                                          "message": "User does not have STUDENT role"
                                        }
                                        """
                        )
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "The project was not found or belongs to another user",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class),
                        examples = @ExampleObject(
                                value = """
                                        {
                                          "message": "Project not found: 123"
                                        }
                                        """
                        )
                )
        ),
        @ApiResponse(
                responseCode = "500",
                description = "Unexpected internal server error",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class),
                        examples = @ExampleObject(
                                value = """
                                        {
                                          "message": "Unknown error"
                                        }
                                        """
                        )
                )
        )
})
public @interface GetProjectImplementationByIdDocs {
}
