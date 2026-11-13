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
        summary = "Update project via frontend",
        description = """
                Updates an already exist project on behalf of a Telegram user based on data
                sent from the frontend.
                
                Headers:
                - `X-Telegram-User-Id` — Telegram user ID
                - `X-User-Roles` — Telegram user roles
                
                Request Body:
                - `github_repository_url` — GitHub repository URL
                - `deployment_url` — project deployment url
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
                                                  "deployment_url": "https://my.server/currency-exchange-project"
                                                }
                                                """
                                )
                        }
                )
        )
)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Project successfully updated",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = Project.class),
                        examples = @ExampleObject(value = """
                                {
                                  "id": 1,
                                  "author_telegram_user_id": 123456789,
                                  "github_repository_url": "https://github.com/zhukovsd/currency-exchange-test",
                                  "programming_language": "Java",
                                  "roadmap_project": "CURRENCY_EXCHANGE",
                                  "added_timestamp": 1793989407,
                                  "deployment_url": "https://my.server/currency-exchange-project"
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
                responseCode = "404",
                description = "Project not found or belongs to another user",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class),
                        examples = @ExampleObject(
                                value = """
                                        {
                                        "message": "Project not found: Project with specified ID does not exist"
                                        }
                                        """
                        )
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
                responseCode = "400",
                description = "Input data validation error",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class),
                        examples = {
                                @ExampleObject(
                                        name = "Missing X-Telegram-User-Id header",
                                        value = """
                                                {
                                                  "message": "Missing required header: X-Telegram-User-Id"
                                                }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Missing X-Telegram-User-Roles header",
                                        value = """
                                                {
                                                  "message": "Missing required header: X-Telegram-User-Roles"
                                                }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Invalid project id parameter",
                                        value = """
                                                {
                                                  "message": "Invalid value for parameter: id"
                                                }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Invalid github repository url",
                                        value = """
                                                {
                                                  "message": "githubRepositoryUrl: URL must be a valid GitHub https URL"
                                                }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Empty or null github repository url",
                                        value = """
                                                {
                                                  "message": "Invalid input: GitHub repository URL cannot be empty"
                                                }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Invalid deployment url",
                                        value = """
                                                {
                                                  "message": "deploymentUrl: URL must be a valid https URL"
                                                }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Empty deployment url",
                                        value = """
                                                {
                                                  "message": "Invalid input: Deployment URL cannot be empty"
                                                }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Project does not support deployment url",
                                        value = """
                                                {
                                                  "message": "Invalid input: The project does not support deployment"
                                                }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Request body contains invalid fields",
                                        value = """
                                                {
                                                  "message": "Invalid request body"
                                                }
                                                """
                                )
                        }
                )
        )
})
public @interface UpdateProjectViaFrontendDocs {
}