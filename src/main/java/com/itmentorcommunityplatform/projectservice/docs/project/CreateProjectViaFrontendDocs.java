package com.itmentorcommunityplatform.projectservice.docs.project;

import com.itmentorcommunityplatform.projectservice.dto.ErrorResponse;
import com.itmentorcommunityplatform.projectservice.model.Project;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Create project via frontend",
        description = """
                Creates a new project on behalf of a Telegram user based on data
                sent from the frontend.
                
                Headers:
                - `X-Telegram-User-Id` — Telegram user ID
                - `X-Telegram-Username` — Telegram username
                
                Request Body:
                - `github_repository_url` — GitHub repository URL
                - `programming_language` — project programming language
                - `roadmap_project` — project identifier in the roadmap (available options:
                        HANGMAN, SIMULATION, CURRENCY-EXCHANGE, TENNIS-SCOREBOARD, WEATHER-VIEWER,
                        CLOUD-FILE-STORAGE, TASK-TRACKER, OTHER )
                """,
        requestBody = @RequestBody(
                required = true,
                content = @Content(
                        mediaType = "application/json",
                        examples = {
                                @ExampleObject(
                                        value = """
                                                {
                                                  "github_repository_url": "https://github.com/zhukovsd/currency-exchange-test",
                                                  "programming_language": "Java",
                                                  "roadmap_project": "CURRENCY-EXCHANGE"
                                                }
                                                """
                                )
                        }
                )
        )
)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "201",
                description = "Project successfully created",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = Project.class),
                        examples = @ExampleObject(value = """
                                {
                                  "id": 1,
                                  "author_telegram_user_id": 123456789,
                                  "github_repository_url": "https://github.com/zhukovsd/currency-exchange-test",
                                  "programming_language": "Java",
                                  "roadmap_project": "CURRENCY-EXCHANGE",
                                  "added_timestamp": 1765897706
                                }
                                """)
                )
        ),
        @ApiResponse(
                responseCode = "409",
                description = "Duplicate project",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class),
                        examples = @ExampleObject(
                                value = """
                                        {
                                        "message": "The project at this repository link already exists"
                                        }
                                        """
                        )
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Input data validation error",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class),
                        examples = {
                                @ExampleObject(
                                        name = "Invalid URL",
                                        value = """
                                                {
                                                  "message": "Invalid GitHub repository URL"
                                                }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Empty required field programming_language",
                                        value = """
                                                {
                                                  "message": "Field 'programming_language' must not be empty"
                                                }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Missing X-Telegram-User-Id header",
                                        value = """
                                                {
                                                  "message": "Missing required header: X-Telegram-User-Id"
                                                }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Unknown project type",
                                        value = """
                                                {
                                                  "message": "Unknown roadmap project type"
                                                }
                                                """
                                )
                        }
                )
        )
})
public @interface CreateProjectViaFrontendDocs {
}