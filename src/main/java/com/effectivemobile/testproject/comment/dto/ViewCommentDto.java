package com.effectivemobile.testproject.comment.dto;

import com.effectivemobile.testproject.user.dto.ViewUserDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.effectivemobile.testproject.utility.Constant.DATE_TIME_PATTERN;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Информация о комментарии")
public class ViewCommentDto {
    @Schema(description = "ID комментария", example = "1")
    private Integer id;

    @Schema(description = "Текст комментария", example = "Текст комментария =)")
    private String text;

    private ViewUserDto author;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    @Schema(description = "Дата и время создания комментария", example = "2023-12-08 09:10:50")
    private LocalDateTime created;
}
