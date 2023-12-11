package com.effectivemobile.testproject.task.dto;

import com.effectivemobile.testproject.task.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateTaskStatusDto {
    @NotNull
    private TaskStatus taskStatus;
}
