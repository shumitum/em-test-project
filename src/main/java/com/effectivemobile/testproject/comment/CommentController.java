package com.effectivemobile.testproject.comment;

import com.effectivemobile.testproject.comment.dto.CreateCommentDto;
import com.effectivemobile.testproject.comment.dto.UpdateCommentDto;
import com.effectivemobile.testproject.comment.dto.ViewCommentDto;
import com.effectivemobile.testproject.user.User;
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
    public void createComment(@AuthenticationPrincipal User user,
                              @RequestBody @Valid CreateCommentDto createCommentDto) {

    }

    @PatchMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateComment(@PathVariable @Positive Integer commentId,
                              @RequestBody @Valid UpdateCommentDto updateCommentDto,
                              @AuthenticationPrincipal User user) {

    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable @Positive Integer commentId,
                              @AuthenticationPrincipal User user) {

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ViewCommentDto> getCommentsByTaskId(@RequestParam @Positive Integer taskId) {

        return null;
    }

}
