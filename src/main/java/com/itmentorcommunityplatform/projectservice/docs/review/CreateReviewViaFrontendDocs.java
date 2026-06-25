package com.itmentorcommunityplatform.projectservice.docs.review;

import com.itmentorcommunityplatform.projectservice.dto.ErrorResponse;
import com.itmentorcommunityplatform.projectservice.dto.review.ReviewResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
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
        summary = "Добавление ревью проекта с фронтенда",
        description = "Создаёт ревью для существующего проекта. Telegram user id автора ревью берётся из заголовка X-Telegram-User-Id.",
        requestBody = @RequestBody(
                required = true,
                content = @Content(
                        mediaType = "application/json",
                        examples = @ExampleObject(
                                value = """
                                        {
                                          "project_github_repository_url": "https://github.com/zhukovsd/simulation",
                                          "review_url": "https://github.com/zhukovsd/simulation/pull/1"
                                        }
                                        """
                        )
                )
        ),
        responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Ревью успешно создано",
                        headers = @Header(
                                name = "Content-Type",
                                description = "application/json"
                        ),
                        content = @Content(schema = @Schema(implementation = ReviewResponse.class))
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Некорректный запрос",
                        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Проект не найден",
                        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                )
        }
)
public @interface CreateReviewViaFrontendDocs {
}