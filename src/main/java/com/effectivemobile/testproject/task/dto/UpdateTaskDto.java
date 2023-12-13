package com.effectivemobile.testproject.task.dto;

import com.effectivemobile.testproject.task.TaskPriority;
import com.effectivemobile.testproject.task.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Информация для обновления задачи")
public class UpdateTaskDto {
    @Size(max = 50)
    @Schema(description = "Заголовок задачи, не более 50 символов", example = "Заголовок задачи")
    private String header;

    @Size(max = 1000)
    @Schema(description = "Описание задачи, не более 1000 символов", example = "Описание задачи")
    private String description;

    @Schema(description = "Статус задачи", example = "IN_PROGRESS")
    private TaskStatus taskStatus;

    @Schema(description = "Приоритет задачи", example = "LOW")
    private TaskPriority taskPriority;

    @Positive
    @Schema(description = "ID исполнителя задачи", example = "1")
    private Integer executorId;
}
