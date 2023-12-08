package com.effectivemobile.testproject.comment.dto;

import com.effectivemobile.testproject.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewCommentDto {

    private String text;
    private UserDto commentator;
    private LocalDateTime created;
}
