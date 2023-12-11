package com.effectivemobile.testproject.task;

import com.effectivemobile.testproject.task.dto.CreateTaskDto;
import com.effectivemobile.testproject.task.dto.UpdateTaskDto;
import com.effectivemobile.testproject.task.dto.UpdateTaskStatusDto;
import com.effectivemobile.testproject.task.dto.ViewTaskDto;

import java.util.List;

public interface TaskService {
    ViewTaskDto createTask(Integer userId, CreateTaskDto createTaskDto);

    ViewTaskDto updateTaskById(Integer taskId, UpdateTaskDto updateTaskDto);

    void deleteTask(Integer taskId, Integer userId);

    ViewTaskDto getTaskById(Integer taskId);

    ViewTaskDto changeTaskStatus(Integer taskId, UpdateTaskStatusDto updateStatus);

    List<ViewTaskDto> getTaskByExecutorId(Integer executorId, TaskPriority priority, int from, int size);

    List<ViewTaskDto> getTaskByAuthorId(Integer authorId, TaskPriority priority, int from, int size);
}
