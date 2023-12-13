package com.effectivemobile.testproject.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "Описание ошибки")
public class ErrorResponse {
    @Schema(description = "Error message", example = "must not be blank. Value: null")
    private String message;

    @Schema(description = "Error status", example = "400 BAD_REQUEST")
    private String status;

    @Schema(example = "user/1/tasks")
    private String path;

    @Schema(example = "2023-12-08 09:10:50")
    private String timestamp;
}