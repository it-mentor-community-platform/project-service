package com.itmentorcommunityplatform.projectservice.docs.project;

import com.itmentorcommunityplatform.projectservice.dto.ErrorResponse;
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
        summary = "Get current user's projects summary",
        description = """
                Returns a summary of the current user's projects grouped by roadmap project.

                Each key is a roadmap project (e.g. HANGMAN, SIMULATION, CURRENCY_EXCHANGE, TENNIS_SCOREBOARD,
                WEATHER_VIEWER, CLOUD_FILE_STORAGE, TASK_TRACKER, OTHER),
                each value is a list of programming languages used in projects of that
                roadmap type for the current user.

                Required headers:
                - `X-Telegram-User-Id` — Telegram user ID
                - `X-User-Roles` — user roles (access allowed only for STUDENT)
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
                        required = false,
                        description = "User roles. Access is allowed only for STUDENT",
                        example = "STUDENT"
                )
        }
)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Summary successfully returned",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(
                                type = "object",
                                additionalProperties = Schema.AdditionalPropertiesValue.TRUE
                        ),
                        examples = {
                                @ExampleObject(
                                        name = "Non-empty summary",
                                        value = """
                                                {
                                                  "CURRENCY_EXCHANGE": ["Java", "C#"],
                                                  "HANGMAN": ["Rust"],
                                                  "SIMULATION": ["Java"]
                                                }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Empty summary",
                                        value = "{}"
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
public @interface GetProjectsSummaryDocs {
}
