package com.effectivemobile.testproject.task;

import com.effectivemobile.testproject.task.dto.CreateTaskDto;
import com.effectivemobile.testproject.task.dto.UpdateTaskDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@WithUserDetails("mail@mail.com")
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    private CreateTaskDto createTaskDto;

    private UpdateTaskDto updateTaskDto;

    @BeforeEach
    void setUp() {
        createTaskDto = CreateTaskDto.builder()
                .header("header")
                .description("description")
                .taskPriority(TaskPriority.MEDIUM)
                .executorId(1)
                .build();

        updateTaskDto = UpdateTaskDto.builder()
                .header("new header")
                .description("new description")
                .taskPriority(TaskPriority.LOW)
                .taskStatus(TaskStatus.IN_PROGRESS)
                .executorId(2)
                .build();
    }


    @Test
    @SneakyThrows
    void createTask_whenInvokeWithValidDto_thenReturnCreateStatus() {
        mockMvc.perform(post("/tasks")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createTaskDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @SneakyThrows
    void updateTask_whenInvokeWithValidDto_thenReturnOkStatus() {
        mockMvc.perform(patch("/tasks/{taskId}", 1)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updateTaskDto)))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void updateTaskStatus_whenInvokeWithCorrectStatus_thenReturnOkStatus() {
        mockMvc.perform(patch("/tasks/{taskId}/status", 1)
                        .param("status", "IN_PROGRESS"))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void deleteTask_whenInvokeWithCorrectId_thenReturnNoContentStatus() {
        mockMvc.perform(delete("/tasks/{taskId}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    void getTaskById_whenInvokeWithCorrectTaskId_thenReturnOkStatus() {
        mockMvc.perform(get("/tasks/{taskId}", 1))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void getTaskByExecutorId_whenInvoke_returnOkStatus() {
        mockMvc.perform(get("/tasks/search")
                        .param("aore", "AUTHOR")
                        .param("userId", "1")
                        .param("priority", "MEDIUM")
                        .param("from", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }
}