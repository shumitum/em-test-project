package com.effectivemobile.testproject.task.dto;

import com.effectivemobile.testproject.task.TaskPriority;
import com.effectivemobile.testproject.task.TaskStatus;
import com.effectivemobile.testproject.user.User;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ViewTaskDto {
    private Integer id;
    private String header;
    private String description;
    private TaskStatus taskStatus;
    private TaskPriority taskPriority;
    private User author;
    private User executor;
}
