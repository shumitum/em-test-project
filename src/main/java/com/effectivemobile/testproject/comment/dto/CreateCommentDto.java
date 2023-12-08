package com.effectivemobile.testproject.comment.dto;

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
public class CreateCommentDto {
    @NotBlank
    @Size(max = 1000)
    private String text;

    @NotNull
    private Integer userId;

    @NotNull
    private Integer taskId;
}
