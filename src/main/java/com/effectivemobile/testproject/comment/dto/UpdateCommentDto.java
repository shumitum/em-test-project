package com.effectivemobile.testproject.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Обновленный текст комментария")
public class UpdateCommentDto {

    @NotBlank
    @Size(max = 1000)
    @Schema(description = "Обновленный текст комментария", example = "Новый текст комментария")
    private String text;
}
