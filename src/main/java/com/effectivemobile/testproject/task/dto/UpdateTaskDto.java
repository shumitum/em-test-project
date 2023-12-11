package com.effectivemobile.testproject.task.dto;

import com.effectivemobile.testproject.task.TaskPriority;
import com.effectivemobile.testproject.task.TaskStatus;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateTaskDto {

    @Size(max = 50)
    private String header;

    @Size(max = 1000)
    private String description;

    private TaskStatus taskStatus; // если таск креатед то можно поменять только исполнителя и отредактировать и описание хедер

    private TaskPriority taskPriority;

    @Positive
    private Integer executorId;
}
