package com.effectivemobile.testproject.comment;

import com.effectivemobile.testproject.comment.dto.CreateCommentDto;
import com.effectivemobile.testproject.comment.dto.UpdateCommentDto;
import com.effectivemobile.testproject.comment.dto.ViewCommentDto;
import com.effectivemobile.testproject.user.User;
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
@RequestMapping("/comments")
@Tag(name = "КОММЕНТАРИИ", description = "API для работы с комментариями")
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "Создание комментария к задаче",
            description = "Все пользователи могут комментировать задачи друг друга")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Комментарий создан",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ViewCommentDto.class))}),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ViewCommentDto createComment(@AuthenticationPrincipal User user,
                                        @RequestBody @Valid CreateCommentDto createCommentDto) {
        log.info("Запрос на создание нового комментария к задаче с ID={} от пользователя с ID={}",
                createCommentDto.getTaskId(), user.getId());
        return commentService.createComment(user.getId(), createCommentDto);
    }

    @Operation(summary = "Редактирование комментария",
            description = "Пользователи могут редактировать свои комментарии")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий отредактирован",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ViewCommentDto.class))}),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content)
    })
    @PatchMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public ViewCommentDto updateComment(@Parameter(description = "ID редактируемого комментария")
                                        @PathVariable("commentId") @Positive Integer commentId,
                                        @RequestBody @Valid UpdateCommentDto updateCommentDto,
                                        @AuthenticationPrincipal User user) {
        log.info("Запрос на обновление комментария с ID={} от пользователя с ID={}", commentId, user.getId());
        return commentService.updateComment(commentId, user.getId(), updateCommentDto);
    }

    @Operation(summary = "Удаление комментария",
            description = "Пользователи могут удалять свои комментарии")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Задача удалена"),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content)
    })
    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@Parameter(description = "ID удаляемого комментария")
                              @PathVariable("commentId") @Positive Integer commentId,
                              @AuthenticationPrincipal User user) {
        log.info("Запрос на удаление комментария с ID={} от пользователя с ID={}", commentId, user.getId());
        commentService.deleteComment(commentId, user.getId());
    }

    @Operation(summary = "Поиск комментариев к конкретной задаче", description = "Поиск комментариев к конкретной задаче " +
            "(по ID задачи) и пагинацией возвращаемых данных. Если по запросу не найдено ни одного комментария, " +
            "то возвращается пустой список ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарии найдены",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ViewCommentDto.class))}),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content)
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ViewCommentDto> getCommentsByTaskId(@Parameter(description = "ID задачи для которой запрашиваются комментарии")
                                                    @RequestParam("taskId") @Positive Integer taskId,
                                                    @Parameter(description = "Количество элементов, которые нужно " +
                                                            "пропустить для формирования текущего набора")
                                                    @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero int from,
                                                    @Parameter(description = "количество элементов в наборе")
                                                    @RequestParam(name = "size", defaultValue = "10") @Positive int size) {
        log.info("Запрос списка комментарием к задаче ID={}", taskId);
        return commentService.getCommentsByTaskId(taskId, from, size);
    }
}
