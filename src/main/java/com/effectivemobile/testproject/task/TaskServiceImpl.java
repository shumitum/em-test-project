package com.effectivemobile.testproject.task;

import com.effectivemobile.testproject.task.dto.CreateTaskDto;
import com.effectivemobile.testproject.task.dto.UpdateTaskDto;
import com.effectivemobile.testproject.task.dto.UpdateTaskStatusDto;
import com.effectivemobile.testproject.task.dto.ViewTaskDto;
import com.effectivemobile.testproject.user.Role;
import com.effectivemobile.testproject.user.User;
import com.effectivemobile.testproject.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TaskMapper taskMapper;


    @Override
    @Transactional
    public ViewTaskDto createTask(Integer userId, CreateTaskDto createTaskDto) {
        final User taskAuthor = userService.getUserById(userId);
        final User taskExecutor = userService.getUserById(createTaskDto.getExecutorId());
        if (taskAuthor.getRole().equals(Role.EXECUTOR) && taskExecutor.getRole().equals(Role.MANAGER)) {
            throw new IllegalArgumentException(
                    "Пользователь с ролю 'Исполнитель' не может назначать задачу пользователю с ролью 'Менеджер'");
        }
        Task task = taskMapper.toTask(createTaskDto);
        task.setAuthor(taskAuthor);
        task.setExecutor(taskExecutor);
        final ViewTaskDto newTask = taskMapper.toViewTaskDto(taskRepository.save(task));
        log.info("Создана новая задача: {}", newTask);
        return newTask;
    }

    @Override
    @Transactional
    public ViewTaskDto updateTaskById(Integer taskId, UpdateTaskDto updateTaskDto) {
        return null;
    }

    @Override
    @Transactional
    public ViewTaskDto changeTaskStatus(Integer taskId, UpdateTaskStatusDto updateStatus) {
        return null;
    }

    @Override
    @Transactional
    public void deleteTask(Integer taskId, Integer userId) {

    }

    @Override
    @Transactional(readOnly = true)
    public ViewTaskDto getTaskById(Integer taskId) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ViewTaskDto> getTaskByExecutorId(Integer executorId, TaskPriority priority, int from, int size) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ViewTaskDto> getTaskByAuthorId(Integer authorId, TaskPriority priority, int from, int size) {
        return null;
    }
}
