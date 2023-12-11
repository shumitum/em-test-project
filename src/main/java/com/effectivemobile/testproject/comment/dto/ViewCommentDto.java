package com.effectivemobile.testproject.comment.dto;

import com.effectivemobile.testproject.user.dto.ViewUserDto;
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
    private ViewUserDto commentator;
    private LocalDateTime created;
}
