package com.effectivemobile.testproject.task.dto;

import com.effectivemobile.testproject.task.TaskPriority;
import com.effectivemobile.testproject.task.TaskStatus;
import com.effectivemobile.testproject.user.dto.ViewUserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Информация о задаче")
public class ViewTaskDto {
    @Schema(description = "ID задачи", example = "1")
    private Integer id;

    @Schema(description = "Заголовок задачи, не более 50 символов", example = "Заголовок задачи")
    private String header;

    @Schema(description = "Описание задачи, не более 1000 символов", example = "Описание задачи")
    private String description;

    @Schema(description = "Статус задачи", example = "IN_PROGRESS")
    private TaskStatus taskStatus;

    @Schema(description = "Приоритет задачи", example = "HIGH")
    private TaskPriority taskPriority;

    private ViewUserDto author;

    private ViewUserDto executor;
}
