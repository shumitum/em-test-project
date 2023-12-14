package com.effectivemobile.testproject.task;

import com.effectivemobile.testproject.task.dto.CreateTaskDto;
import com.effectivemobile.testproject.task.dto.UpdateTaskDto;
import com.effectivemobile.testproject.task.dto.ViewTaskDto;
import com.effectivemobile.testproject.user.Role;
import com.effectivemobile.testproject.user.User;
import com.effectivemobile.testproject.user.UserService;
import com.effectivemobile.testproject.utility.AuthorOrExecutor;
import com.effectivemobile.testproject.utility.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
        if (taskAuthor.getRole().equals(Role.EXECUTOR) && !taskAuthor.getId().equals(taskExecutor.getId())
        ) {
            throw new IllegalArgumentException(
                    "Пользователь с ролю 'Исполнитель' не может создавать задачи для других пользователей");

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
    public ViewTaskDto updateTaskById(Integer taskId, Integer userId, UpdateTaskDto updateTaskDto) {
        checkUserIsTaskCreator(taskId, userId);
        Integer newExecutorId = updateTaskDto.getExecutorId();
        userService.checkUserExistence(newExecutorId);
        final Task updatingTask = findTaskById(taskId);

        if (!updatingTask.getExecutor().getId().equals(newExecutorId)) {
            if (updatingTask.getAuthor().getRole().equals(Role.MANAGER)) {
                updatingTask.setExecutor(userService.getUserById(newExecutorId));
            } else {
                throw new IllegalArgumentException("Исполнитель не может переназначить задачу другому пользователю");
            }
        }

        TaskStatus newStatus = updateTaskDto.getTaskStatus();
        if (newStatus != null) {
            setTaskStatus(userId, updatingTask, newStatus);
        }

        Optional.ofNullable(updateTaskDto.getHeader()).ifPresent(updatingTask::setHeader);
        Optional.ofNullable(updateTaskDto.getDescription()).ifPresent(updatingTask::setDescription);
        Optional.ofNullable(updateTaskDto.getTaskPriority()).ifPresent(updatingTask::setTaskPriority);
        return taskMapper.toViewTaskDto(updatingTask);
    }

    private void setTaskStatus(Integer userId, Task updatingTask, TaskStatus newStatus) {
        if (updatingTask.getTaskStatus().equals(TaskStatus.CANCELED)
                && !updatingTask.getAuthor().getId().equals(userId)) {
            throw new IllegalArgumentException("Исполнитель не может поменять статус отменённой задачи");
        }

        if (newStatus.equals(TaskStatus.CREATED)) {
            throw new IllegalArgumentException("Запрещено вручную устанавливать статус задачи 'Создано'");
        }

        if (newStatus.equals(TaskStatus.PENDING)
                || newStatus.equals(TaskStatus.IN_PROGRESS)
                || newStatus.equals(TaskStatus.COMPLETED)) {
            if (updatingTask.getExecutor().getId().equals(userId)) {
                updatingTask.setTaskStatus(newStatus);
            } else {
                throw new IllegalArgumentException(
                        "Только исполнителю разрешено менять статус только на 'В ожидании', 'В работе', 'Завершено'");
            }
        }

        if (newStatus.equals(TaskStatus.CANCELED)) {
            if (updatingTask.getAuthor().getId().equals(userId)) {
                updatingTask.setTaskStatus(newStatus);
            } else {
                throw new IllegalArgumentException(
                        "Только автору задачи разрешено менять статус на 'Отменено'");
            }
        }
    }

    @Override
    @Transactional
    public ViewTaskDto updateTaskStatus(Integer taskId, Integer userId, TaskStatus newStatus) {
        final Task updatingTask = findTaskById(taskId);
        if (updatingTask.getExecutor().getId().equals(userId) || updatingTask.getAuthor().getId().equals(userId)) {
            setTaskStatus(userId, updatingTask, newStatus);
        } else {
            throw new IllegalArgumentException("Только автору и исполнителю разрешено менять статус задачи");
        }
        return taskMapper.toViewTaskDto(updatingTask);
    }

    @Override
    @Transactional
    public void deleteTask(Integer taskId, Integer userId) {
        checkUserIsTaskCreator(taskId, userId);
        final Task task = findTaskById(taskId);
        if (task.getTaskStatus().equals(TaskStatus.CREATED)
                || task.getTaskStatus().equals(TaskStatus.CANCELED)) {
            taskRepository.deleteById(taskId);
        } else {
            throw new IllegalArgumentException("Удалять можно только задачи в статусе 'Создано' или 'Отменено'");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ViewTaskDto getTaskById(Integer taskId) {
        return taskMapper.toViewTaskDto(findTaskById(taskId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ViewTaskDto> searchTasks(AuthorOrExecutor authorOrExecutor,
                                         Integer userId,
                                         TaskPriority priority,
                                         int from, int size) {
        userService.checkUserExistence(userId);
        List<Task> tasks = Collections.emptyList();
        if (authorOrExecutor.equals(AuthorOrExecutor.EXECUTOR)) {
            tasks = taskRepository.searchTasksByExecutor(userId, priority, PageParam.of(from, size));
        } else if (authorOrExecutor.equals(AuthorOrExecutor.AUTHOR)) {
            tasks = taskRepository.searchTasksByAuthor(userId, priority, PageParam.of(from, size));
        }
        return tasks.stream()
                .map(taskMapper::toViewTaskDto)
                .toList();
    }

    public Task findTaskById(Integer taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Задачи с ID=%d не существует", taskId)));
    }

    private void checkUserIsTaskCreator(Integer taskId, Integer userId) {
        if (!findTaskById(taskId).getAuthor().getId().equals(userId)) {
            throw new IllegalArgumentException(
                    String.format("Пользователь с ID=%d не является автором задачи с ID=%d", userId, taskId));
        }
    }
}