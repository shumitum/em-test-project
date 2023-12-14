package com.effectivemobile.testproject.task;

import com.effectivemobile.testproject.comment.CommentMapper;
import com.effectivemobile.testproject.comment.CommentRepository;
import com.effectivemobile.testproject.task.dto.CreateTaskDto;
import com.effectivemobile.testproject.task.dto.UpdateTaskDto;
import com.effectivemobile.testproject.task.dto.ViewTaskDto;
import com.effectivemobile.testproject.user.Role;
import com.effectivemobile.testproject.user.User;
import com.effectivemobile.testproject.user.UserService;
import com.effectivemobile.testproject.user.dto.ViewUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserService userService;

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private TaskMapper taskMapper;

    private ViewTaskDto viewTaskDto;

    private User user;

    private CreateTaskDto createTaskDto;
    private Task task;


    @BeforeEach
    void setUp() {
        viewTaskDto = ViewTaskDto.builder()
                .header("header")
                .description("description")
                .taskPriority(TaskPriority.MEDIUM)
                .taskStatus(TaskStatus.IN_PROGRESS)
                .author(new ViewUserDto())
                .executor(new ViewUserDto())
                .build();

        task = Task.builder()
                .id(1)
                .header("header")
                .description("description")
                .taskPriority(TaskPriority.MEDIUM)
                .taskStatus(TaskStatus.IN_PROGRESS)
                .author(user)
                .executor(user)
                .build();

        user = User.builder()
                .id(1)
                .role(Role.EXECUTOR)
                .build();

        createTaskDto = CreateTaskDto.builder()
                .executorId(1)
                .build();
    }

    @Test
    void createTask() {
        when(taskMapper.toViewTaskDto(any())).thenReturn(viewTaskDto);
        when(taskMapper.toTask(createTaskDto)).thenReturn(task);
        when(userService.getUserById(anyInt())).thenReturn(user);

        ViewTaskDto actualTask = taskService.createTask(anyInt(), createTaskDto);

        assertEquals(viewTaskDto, actualTask);
    }

    @Test
    void updateTaskById_whenInvokeWithInvalidTaskId_thenTrowNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> taskService.updateTaskById(1, 1, new UpdateTaskDto()));
    }

    @Test
    void deleteTask_whenInvokeWithInvalidTaskId_thenTrowNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> taskService.deleteTask(1, 1));
    }

    @Test
    void getTaskById() {
        when(taskRepository.findById(anyInt())).thenReturn(Optional.of(task));
        when(taskMapper.toViewTaskDto(any())).thenReturn(viewTaskDto);

        ViewTaskDto actualTask = taskService.getTaskById(anyInt());

        assertEquals(viewTaskDto, actualTask);
    }
}