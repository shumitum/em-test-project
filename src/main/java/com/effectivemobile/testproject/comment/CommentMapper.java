package com.effectivemobile.testproject.comment;

import com.effectivemobile.testproject.comment.dto.CreateCommentDto;
import com.effectivemobile.testproject.comment.dto.ViewCommentDto;
import com.effectivemobile.testproject.user.UserMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CommentMapper {

    ViewCommentDto toViewCommentDto(Comment comment);

    Comment toComment(CreateCommentDto createCommentDto);
}
