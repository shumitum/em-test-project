package com.effectivemobile.testproject.task;

import com.effectivemobile.testproject.task.dto.CreateTaskDto;
import com.effectivemobile.testproject.task.dto.UpdateTaskDto;
import com.effectivemobile.testproject.task.dto.ViewTaskDto;
import com.effectivemobile.testproject.utility.AuthorOrExecutor;

import java.util.List;

public interface TaskService {
    ViewTaskDto createTask(Integer userId, CreateTaskDto createTaskDto);

    ViewTaskDto updateTaskById(Integer taskId, Integer userId, UpdateTaskDto updateTaskDto);

    void deleteTask(Integer taskId, Integer userId);

    ViewTaskDto getTaskById(Integer taskId);

    ViewTaskDto updateTaskStatus(Integer taskId, Integer userId, TaskStatus taskStatus);

    List<ViewTaskDto> searchTasks(AuthorOrExecutor authorOrExecutor,
                                  Integer userId,
                                  TaskPriority priority,
                                  int from, int size);
}
