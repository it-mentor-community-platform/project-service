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

@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Create project via Telegram bot or Data Importer",
        description = """
                Processes an internal request to create a project from a Telegram bot
                or a data import service.
                
                Depending on the `project_source_type` value:
                - `TELEGRAM_BOT` — the project is created by a Telegram bot.
                - `DATA_IMPORTER` — the project is created during data import from an external source.
                
                Request body fields:
                - `author_telegram_user_id` — Telegram ID of the project author
                - `github_repository_url` — GitHub repository URL
                - `programming_language` — project programming language
                - `roadmap_project` — project identifier in the roadmap
                  (available options: HANGMAN, SIMULATION, CURRENCY-EXCHANGE, TENNIS-SCOREBOARD,
                  WEATHER-VIEWER, CLOUD-FILE-STORAGE, TASK-TRACKER, OTHER)
                - `author_telegram_username` — Telegram username
                - `added_timestamp` — project addition time (used only for DATA_IMPORTER;
                  ignored and overwritten with current time for TELEGRAM_BOT source)
                - `project_source_type` — project source (`TELEGRAM_BOT` or `DATA_IMPORTER`)
                """,
        requestBody = @RequestBody(
                required = true,
                content = @Content(
                        mediaType = "application/json",
                        examples = {
                                @ExampleObject(
                                        name = "Creation via Telegram bot",
                                        value = """
                                                {
                                                  "author_telegram_user_id": 123,
                                                  "github_repository_url": "https://github.com/zhukovsd/hangman",
                                                  "programming_language": "Java",
                                                  "roadmap_project": "HANGMAN",
                                                  "author_telegram_username": "zhukovsd",
                                                  "project_source_type": "TELEGRAM_BOT"
                                                }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Creation via Data Importer",
                                        value = """
                                                {
                                                  "author_telegram_user_id": 123,
                                                  "github_repository_url": "https://github.com/zhukovsd/hangman",
                                                  "programming_language": "Java",
                                                  "roadmap_project": "HANGMAN",
                                                  "author_telegram_username": "zhukovsd",
                                                  "added_timestamp": 1765628000,
                                                  "project_source_type": "DATA_IMPORTER"
                                                }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Creation via Telegram bot without field author_telegram_username",
                                        value = """
                                                {
                                                  "author_telegram_user_id": 123,
                                                  "github_repository_url": "https://github.com/zhukovsd/hangman",
                                                  "programming_language": "Java",
                                                  "roadmap_project": "HANGMAN",
                                                  "project_source_type": "TELEGRAM_BOT"
                                                }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Creation via Data Importer without field author_telegram_username",
                                        value = """
                                                {
                                                  "author_telegram_user_id": 123,
                                                  "github_repository_url": "https://github.com/zhukovsd/hangman",
                                                  "programming_language": "Java",
                                                  "roadmap_project": "HANGMAN",
                                                  "added_timestamp": 1765628000,
                                                  "project_source_type": "DATA_IMPORTER"
                                                }
                                                """
                                ),
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
                        examples = @ExampleObject(
                                value = """
                                        {
                                          "id": 1,
                                          "author_telegram_user_id": 123,
                                          "github_repository_url": "https://github.com/zhukovsd/hangman",
                                          "programming_language": "Java",
                                          "roadmap_project": "HANGMAN",
                                          "added_timestamp": 1765897706
                                        }
                                        """
                        )
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
                                        name = "Empty required field",
                                        value = """
                                                {
                                                  "message": "Required field must not be empty"
                                                }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Missing author_telegram_user_id",
                                        value = """
                                                {
                                                  "message": "Missing required field: author_telegram_user_id"
                                                }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Invalid project_source_type",
                                        value = """
                                                {
                                                  "message": "Invalid project_source_type value"
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
public @interface CreateProjectViaTelegramBotOrImporterDocs {
}