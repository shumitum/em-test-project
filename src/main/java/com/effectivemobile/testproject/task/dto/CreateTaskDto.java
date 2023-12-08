package com.effectivemobile.testproject.task.dto;

import com.effectivemobile.testproject.task.TaskPriority;
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
    private String header;

    @NotBlank
    @Size(max = 1000)
    private String description;

    @NotNull
    private TaskPriority taskPriority;

    @NotNull
    @Positive
    private Integer executorId;
}
