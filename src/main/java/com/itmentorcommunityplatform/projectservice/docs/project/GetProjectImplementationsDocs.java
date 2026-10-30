package com.itmentorcommunityplatform.projectservice.docs.project;

import com.itmentorcommunityplatform.projectservice.dto.ErrorResponse;
import com.itmentorcommunityplatform.projectservice.dto.response.ProjectResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
        summary = "Get project implementations",
        description = """
                Returns an array of user's project implementations for a specific roadmap project.
                Results are sorted by added_timestamp DESC (newest first).
                Returns an empty array if no implementations are found.

                Valid roadmap projects:
                HANGMAN, SIMULATION, CURRENCY_EXCHANGE, TENNIS_SCOREBOARD,
                WEATHER_VIEWER, CLOUD_FILE_STORAGE, TASK_TRACKER, OTHER.

                Required headers:
                - `X-Telegram-User-Id` — Telegram user ID
                - `X-User-Roles` — user roles (access allowed only for STUDENT)

                Required query parameter:
                - `roadmap_project` — valid roadmap project type
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
                        name = "roadmap_project",
                        in = ParameterIn.QUERY,
                        required = true,
                        description = "Roadmap project type. Must be valid",
                        example = "SIMULATION"
                )
        }
)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Project implementations, or an empty array if none are found",
                content = @Content(
                        mediaType = "application/json",
                        array = @ArraySchema(
                                schema = @Schema(implementation = ProjectResponse.class)
                        ),
                        examples = {
                                @ExampleObject(
                                        name = "Project implementations",
                                        value = """
                                                [
                                                  {
                                                    "id": 421,
                                                    "author_telegram_user_id": 395212293,
                                                    "github_repository_url": "https://github.com/krios2146/simulation_ocaml",
                                                    "programming_language": "OCaml",
                                                    "roadmap_project": "SIMULATION",
                                                    "added_timestamp": 1722459600
                                                  },
                                                  {
                                                    "id": 380,
                                                    "author_telegram_user_id": 395212293,
                                                    "github_repository_url": "https://github.com/krios2146/simulation-go",
                                                    "programming_language": "Go",
                                                    "roadmap_project": "SIMULATION",
                                                    "added_timestamp": 1719781200
                                                  },
                                                  {
                                                    "id": 19,
                                                    "author_telegram_user_id": 395212293,
                                                    "github_repository_url": "https://github.com/krios2146/pet-project-1-simulation",
                                                    "programming_language": "Java",
                                                    "roadmap_project": "SIMULATION",
                                                    "added_timestamp": 1675198800
                                                  }
                                                ]
                                                """
                                ),
                                @ExampleObject(
                                        name = "No project implementations found",
                                        value = "[]"
                                )
                        }
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Missing required header, missing parameter or invalid input",
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
                                        name = "Invalid roadmap project",
                                        value = """
                                                {
                                                  "message": "Invalid value for parameter: roadmap_project"
                                                }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Missing roadmap project",
                                        value = """
                                                {
                                                  "message": "Missing required parameter: roadmap_project"
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
public @interface GetProjectImplementationsDocs {
}