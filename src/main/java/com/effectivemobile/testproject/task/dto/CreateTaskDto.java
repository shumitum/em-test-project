package com.effectivemobile.testproject.task.dto;

import com.effectivemobile.testproject.task.TaskPriority;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateTaskDto {
    @NotBlank
    @Size(max = 50)
    @Schema(description = "Заголовок задачи, не более 50 символов", example = "Заголовок задачи")
    private String header;

    @NotBlank
    @Size(max = 1000)
    @Schema(description = "Описание задачи, не более 1000 символов", example = "Описание задачи")
    private String description;

    @NotNull
    @Schema(description = "Приоритет задачи", example = "HIGH")
    private TaskPriority taskPriority;

    @NotNull
    @Positive
    @Schema(description = "ID исполнителя задачи", example = "1")
    private Integer executorId;
}
