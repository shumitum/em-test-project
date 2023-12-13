package com.effectivemobile.testproject.comment;

import com.effectivemobile.testproject.comment.dto.CreateCommentDto;
import com.effectivemobile.testproject.comment.dto.UpdateCommentDto;
import com.effectivemobile.testproject.comment.dto.ViewCommentDto;
import com.effectivemobile.testproject.task.Task;
import com.effectivemobile.testproject.task.TaskMapper;
import com.effectivemobile.testproject.task.TaskService;
import com.effectivemobile.testproject.user.User;
import com.effectivemobile.testproject.user.UserService;
import com.effectivemobile.testproject.utility.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final TaskService taskService;
    private final UserService userService;
    private final CommentMapper commentMapper;
    private final TaskMapper taskMapper;

    @Override
    @Transactional
    public ViewCommentDto createComment(Integer authorId, CreateCommentDto createCommentDto) {
        final User author = userService.getUserById(authorId);
        final Task task = taskMapper.toTask(taskService.getTaskById(createCommentDto.getTaskId()));
        final Comment newComment = commentMapper.toComment(createCommentDto);
        newComment.setAuthor(author);
        newComment.setTask(task);
        final ViewCommentDto comment = commentMapper.toViewCommentDto(commentRepository.save(newComment));
        log.info("Создан новый комментарий: {}", comment);
        return comment;
    }

    @Override
    @Transactional
    public ViewCommentDto updateComment(Integer commentId, Integer userId, UpdateCommentDto updateCommentDto) {
        final Comment comment = findCommentById(commentId);
        checkUserIsCommentCreator(commentId, userId);
        Optional.ofNullable(updateCommentDto.getText()).ifPresent(comment::setText);
        return commentMapper.toViewCommentDto(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Integer commentId, Integer userId) {
        checkUserIsCommentCreator(commentId, userId);
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ViewCommentDto> getCommentsByTaskId(Integer taskId, int from, int size) {
        return commentRepository.findCommentsByTaskIdOrderByCreated(taskId, PageParam.of(from, size)).stream()
                .map(commentMapper::toViewCommentDto)
                .toList();
    }

    private void checkUserIsCommentCreator(Integer commentId, Integer userId) {
        if (!findCommentById(commentId).getAuthor().getId().equals(userId)) {
            throw new IllegalArgumentException(
                    String.format("Пользователь с ID=%d не является автором комментария с ID=%d", userId, commentId));
        }
    }

    private Comment findCommentById(Integer commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Комментария с ID=%d не существует", commentId)));
    }
}