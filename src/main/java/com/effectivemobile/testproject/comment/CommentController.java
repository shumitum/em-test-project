package com.effectivemobile.testproject.comment;

import com.effectivemobile.testproject.comment.dto.CreateCommentDto;
import com.effectivemobile.testproject.comment.dto.UpdateCommentDto;
import com.effectivemobile.testproject.comment.dto.ViewCommentDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
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
                            schema = @Schema(implementation = Comment.class))}),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTask(@PathVariable @Positive Integer userId,
                           @RequestBody @Valid CreateCommentDto createCommentDto) {

    }

    @PatchMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateComment(@PathVariable @Positive Integer userId,
                              @RequestBody @Valid UpdateCommentDto updateCommentDto) {

    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable @Positive Integer userId,
                              @PathVariable @Positive Integer commentId) {

    }

    @GetMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ViewCommentDto> getCommentsByTaskId(@PathVariable @Positive Integer userId,
                                                    @PathVariable @Positive Integer taskId,
                                                    @ParameterObject Pageable pageable) {

        return null;
    }

}
