package com.effectivemobile.testproject.task;

import com.effectivemobile.testproject.user.Role;
import com.effectivemobile.testproject.user.User;
import com.effectivemobile.testproject.user.UserPosition;
import com.effectivemobile.testproject.user.UserRepository;
import com.effectivemobile.testproject.utility.PageParam;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@DataJpaTest не загружает контекст полностью из-чего в main не загружается AuthService
@SpringBootTest
@Transactional
@Testcontainers
class TaskRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    private User executor;
    private User author;
    private Task task;

    @BeforeEach
    void setUp() {
        executor = User.builder()
                .name("Executor")
                .email("email@email.com")
                .role(Role.EXECUTOR)
                .userPosition(UserPosition.DEV_JUNIOR)
                .password("safgdfg")
                .build();
        author = User.builder()
                .name("Author")
                .email("qmail@qmail.com")
                .role(Role.EXECUTOR)
                .userPosition(UserPosition.DEV_LEAD)
                .password("safgdfg")
                .build();
        task = Task.builder()
                .header("header")
                .description("description")
                .taskPriority(TaskPriority.MEDIUM)
                .author(author)
                .executor(executor)
                .build();
        userRepository.save(executor);
        userRepository.save(author);
        taskRepository.save(task);
    }

    @Test
    void connectionEstablished() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }


    @Test
    @Rollback
    void searchTasksByAuthor_whenInvokeWithValidAuthorId_thenReturnListWithOneTask() {
        List<Task> tasks = taskRepository.searchTasksByAuthor(author.getId(), null, PageParam.of(0, 5));

        assertEquals(1, tasks.size());
        assertEquals(task.getId(), tasks.get(0).getId());
        assertEquals("header", tasks.get(0).getHeader());
    }

    @Test
    @Rollback
    void searchTasksByAuthor_whenInvokeWithWrongAuthorId_thenReturnEmptyList() {
        List<Task> tasks = taskRepository.searchTasksByAuthor(author.getId() + 5, null, PageParam.of(0, 5));

        assertEquals(0, tasks.size());
    }

    @Test
    @Rollback
    void searchTasksByExecutor_whenInvokeWithValidExecutorId_thenReturnListWithOneTask() {
        List<Task> tasks = taskRepository.searchTasksByExecutor(executor.getId(), null, PageParam.of(0, 5));

        assertEquals(1, tasks.size());
        assertEquals(task.getId(), tasks.get(0).getId());
        assertEquals("header", tasks.get(0).getHeader());
    }

    @Test
    @Rollback
    void searchTasksByExecutor_whenInvokeWithWrongExecutorId_thenReturnEmptyList() {
        List<Task> tasks = taskRepository.searchTasksByExecutor(executor.getId() + 5, null, PageParam.of(0, 5));

        assertEquals(0, tasks.size());
    }
}