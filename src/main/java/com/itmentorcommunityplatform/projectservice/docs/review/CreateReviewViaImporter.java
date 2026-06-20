package com.itmentorcommunityplatform.projectservice.docs.review;


import com.itmentorcommunityplatform.projectservice.dto.ErrorResponse;
import com.itmentorcommunityplatform.projectservice.dto.review.ReviewResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Create review via Data Importer",
        description = """
                Processes an internal request to create a review from data import service.
                
                Request body fields:
                - `project_github_repository_url` — GitHub repository URL
                - `review_url` — URL of the review
                - `reviewer_telegram_user_id` — Telegram ID of the reviewer author
                - `added_timestamp` — Review creation timestamp in Unix epoch seconds. Optional;
                """,
        requestBody = @RequestBody(
                required = true,
                content = @Content(
                        mediaType = "application/json",
                        examples = {
                                @ExampleObject(
                                        value = """
                                                    {
                                                      "project_github_repository_url": "https://github.com/test/simulation",
                                                      "review_url": "https://github.com/user/review-test/pull/1",
                                                      "reviewer_telegram_user_id": 123456,
                                                      "added_timestamp": 123
                                                    }
                                                """
                                )
                        }
                )
        ),
        responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Review successfully created",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ReviewResponse.class),
                                examples = @ExampleObject(
                                        value = """
                                                {
                                                    "id": 2,
                                                    "reviewer_telegram_user_id": 123456,
                                                    "url": "https://github.com/user/review-test/pull/1",
                                                    "added_timestamp": 1781548579,
                                                    "project": {
                                                        "id": 1,
                                                        "author_telegram_user_id": 123456,
                                                        "github_repository_url": "https://github.com/test/simulation",
                                                        "programming_language": "JAVA",
                                                        "roadmap_project": "SIMULATION",
                                                        "added_timestamp": 1622505600
                                                    }
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
                                examples = @ExampleObject(
                                        value = """
                                                {
                                                "message": "reviewUrl: Review URL must be a valid https URL"
                                                }
                                                """
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "The project was not found",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponse.class),
                                examples = @ExampleObject(
                                        value = """
                                                {
                                                "message": "Project not found"
                                                }
                                                """
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Unknown error",
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
        }
)
public @interface CreateReviewViaImporter {
}
