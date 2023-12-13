package com.effectivemobile.testproject.comment;

import com.effectivemobile.testproject.comment.dto.CreateCommentDto;
import com.effectivemobile.testproject.comment.dto.UpdateCommentDto;
import com.effectivemobile.testproject.comment.dto.ViewCommentDto;

import java.util.List;

public interface CommentService {
    ViewCommentDto createComment(Integer authorId, CreateCommentDto createCommentDto);

    ViewCommentDto updateComment(Integer commentId, Integer userId, UpdateCommentDto updateCommentDto);

    void deleteComment(Integer commentId, Integer userId);

    List<ViewCommentDto> getCommentsByTaskId(Integer taskId, int from, int size);
}
