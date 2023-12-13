package com.effectivemobile.testproject.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Информация для создания комментария")
public class CreateCommentDto {
    @NotBlank
    @Size(max = 1000)
    @Schema(description = "Текст комментария", example = "Текст комментария =)")
    private String text;

    @NotNull
    @Schema(description = "ID комментатора", example = "1")
    private Integer taskId;
}
