package com.effectivemobile.testproject.task;

import com.effectivemobile.testproject.task.dto.CreateTaskDto;
import com.effectivemobile.testproject.task.dto.UpdateTaskDto;
import com.effectivemobile.testproject.task.dto.ViewTaskDto;
import com.effectivemobile.testproject.user.User;
import com.effectivemobile.testproject.utility.AuthorOrExecutor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
@Tag(name = "ЗАДАЧИ", description = "API для работы с задачами")
public class TaskController {
    private final TaskService taskService;

    @Operation(summary = "Создание задачи", description = "Создание задачи, пользователь МОЖЕТ создать задачу для самого себя," +
            " пользователь с ролью исполнитель не может поставить задачу пользователю с ролью менеджер.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Задача создана",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ViewTaskDto.class))}),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ViewTaskDto createTask(@RequestBody @Valid CreateTaskDto createTaskDto,
                                  @AuthenticationPrincipal User user) {
        log.info("Запрос на создание новой задачи {} от пользователя с ID={}", createTaskDto, user.getId());
        return taskService.createTask(user.getId(), createTaskDto);
    }

    @Operation(summary = "Редактирование задачи", description = "Пользователь может отредактировать задачу если он" +
            " является её владельцем, задача находится в статусе 'Создано', и не принята исполнителем в работу.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача отредактирована",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ViewTaskDto.class))}),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content)
    })
    @PatchMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public ViewTaskDto updateTask(@Parameter(description = "ID обновляемой задачи")
                                  @PathVariable @Positive Integer taskId,
                                  @RequestBody @Valid UpdateTaskDto updateTaskDto,
                                  @AuthenticationPrincipal User user) {
        log.info("Запрос на редактирование задачи по ID={}", taskId);
        return taskService.updateTaskById(taskId, user.getId(), updateTaskDto);
    }

    @Operation(summary = "Смена статуса задачи", description = "Пользователь может поменять статус задачи если он" +
            " является её исполнителем, и задача не отменена автором. Исполнитель не может установить статус" +
            " 'Создано' или 'Отменено'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статус задачи изменён",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ViewTaskDto.class))}),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content)
    })
    @PatchMapping("/{taskId}/status")
    @ResponseStatus(HttpStatus.OK)
    public ViewTaskDto updateTaskStatus(@Parameter(description = "ID обновляемой задачи")
                                        @PathVariable @Positive Integer taskId,
                                        @Parameter(description = "Новый статус задачи")
                                        @RequestParam TaskStatus status,
                                        @AuthenticationPrincipal User user) {
        log.info("Запрос на редактирование задачи по ID={}", taskId);
        return taskService.updateTaskStatus(taskId, user.getId(), status);
    }

    @Operation(summary = "Удаление задачи", description = "Пользователь может удалить задачу если он является её" +
            " владельцем, и задача находится в статусе создана или завершена.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Задача удалена"),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content)
    })
    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@Parameter(description = "ID удаляемой задачи")
                           @PathVariable @Positive Integer taskId,
                           @AuthenticationPrincipal User user) {
        log.info("Запрос на удаление задачи по ID={}", taskId);
        taskService.deleteTask(taskId, user.getId());
    }

    @Operation(summary = "Получение задачи по ID", description = "Авторизованный пользователь может запросить задачу по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача найдена",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ViewTaskDto.class))}),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content)
    })
    @GetMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public ViewTaskDto getTaskById(@Parameter(description = "ID запрашиваемой задачи")
                                   @PathVariable @Positive Integer taskId) {
        log.info("Запрос задачи по ID={}", taskId);
        return taskService.getTaskById(taskId);
    }

    @Operation(summary = "Поиск задач с возможностью фильтрации", description = "Поиск задач c фильтрацией результатов" +
            " поиска по автору или исполнителю, по статусу задач (по умолчанию в выборку попадут задачи с любым" +
            " приоритетом) и пагинацией возвращаемых данных. Если по запросу не найдено ни одного" +
            " события, то возвращается пустой список")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задачи найдены",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ViewTaskDto.class))}),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content)
    })
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<ViewTaskDto> getTaskByExecutorId(@Parameter(description = "Указатель поиска по автору или исполнителю")
                                                 @RequestParam(name = "aoe") AuthorOrExecutor authorOrExecutor,
                                                 @Parameter(description = "ID автора или исполнителя задач")
                                                 @RequestParam @Positive Integer userId,
                                                 @Parameter(description = "Приоритет искомых задач")
                                                 @RequestParam(required = false) TaskPriority priority,
                                                 @Parameter(description = "Количество элементов, которые нужно пропустить" +
                                                         " для формирования текущего набора")
                                                 @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                 @Parameter(description = "количество элементов в наборе")
                                                 @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Запрос списка задач с параметрами, поиск по:{} с ID={}, приоритет: {}, from={}, size={}",
                authorOrExecutor, userId, priority, from, size);
        return taskService.searchTasks(authorOrExecutor, userId, priority, from, size);
    }
}
