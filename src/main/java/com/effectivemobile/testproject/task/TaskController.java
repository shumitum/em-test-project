package com.effectivemobile.testproject.task;

import com.effectivemobile.testproject.task.dto.CreateTaskDto;
import com.effectivemobile.testproject.task.dto.UpdateTaskDto;
import com.effectivemobile.testproject.task.dto.ViewTaskDto;
import com.effectivemobile.testproject.user.User;
import com.effectivemobile.testproject.user.UserPosition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/{userId}/tasks")
@Tag(name = "ЗАДАЧИ", description = "API для работы с задачами")
public class TaskController {
    TaskService taskService;

    @Operation(summary = "Создание задачи", description = "Создание задачи, пользователь МОЖЕТ создать задачу для самого себя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Задача создана",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ViewTaskDto.class))}),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ViewTaskDto createTask(@Parameter(description = "ID пользователя создающего задачу") @PathVariable @Positive Integer userId,
                                  @Parameter(description = "Данные новой задачи")
                                  @RequestBody @Valid CreateTaskDto createTaskDto) {
        return new ViewTaskDto(1, "заголовок", "описание", TaskStatus.CREATED, TaskPriority.MEDIUM, new User(userId, "имя", UserPosition.MANAGER), new User());
    }


    @PatchMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public ViewTaskDto updateTask(@Parameter(description = "ID пользователя") @PathVariable @Positive Integer userId,
                                  @Parameter(description = "ID обновляемой задачи") @PathVariable Integer taskId,
                                  @RequestBody UpdateTaskDto updateTaskDto) {
        return new ViewTaskDto(1, "заголовок", "описание", TaskStatus.CREATED, TaskPriority.MEDIUM, new User(), new User());
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Integer taskId) {

    }

    @Operation(summary = "Получение задачи по её ID")
    @GetMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public void getTaskById(@PathVariable Integer userId,
                            @PathVariable Integer taskId) {

    }
}
