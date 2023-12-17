package com.effectivemobile.testproject.comment;

import com.effectivemobile.testproject.task.Task;
import com.effectivemobile.testproject.task.TaskPriority;
import com.effectivemobile.testproject.task.TaskRepository;
import com.effectivemobile.testproject.user.Role;
import com.effectivemobile.testproject.user.User;
import com.effectivemobile.testproject.user.UserPosition;
import com.effectivemobile.testproject.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@DataJpaTest не загружает контекст полностью из-чего в main не загружается AuthService
@SpringBootTest
@Transactional
@Testcontainers
class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14-alpine");

    private Comment comment;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .name("Junior")
                .email("email@mail.com")
                .role(Role.EXECUTOR)
                .userPosition(UserPosition.DEV_LEAD)
                .password("safgdfg")
                .build();
        Task task = Task.builder()
                .header("header")
                .description("description")
                .taskPriority(TaskPriority.MEDIUM)
                .author(user)
                .executor(user)
                .build();
        comment = Comment.builder()
                .text("text")
                .author(user)
                .task(task)
                .build();

        userRepository.save(user);
        taskRepository.save(task);
        commentRepository.save(comment);
    }

    @Test
    @Rollback
    void findCommentsByTaskIdOrderByCreated_whenInvokeWithValidTaskId_thenReturnListOfComments() {
        List<Comment> comments = commentRepository.findCommentsByTaskIdOrderByCreated(1, PageRequest.of(0, 3));

        assertEquals(1, comments.size());
        assertEquals(comment.getId(), comments.get(0).getId());
        assertEquals("text", comments.get(0).getText());
    }
}