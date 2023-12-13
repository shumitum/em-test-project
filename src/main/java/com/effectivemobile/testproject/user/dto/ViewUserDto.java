package com.effectivemobile.testproject.user.dto;

import com.effectivemobile.testproject.user.Role;
import com.effectivemobile.testproject.user.UserPosition;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Краткая информация о пользователе")
public class ViewUserDto {
    @Schema(description = "ID пользователя", example = "1")
    private Integer id;

    @Schema(description = "Имя пользователя", example = "David")
    private String name;

    @Schema(description = "Должность пользователя", example = "DEV_MIDDLE")
    private UserPosition userPosition;

    @Schema(description = "Роль пользователя", example = "EXECUTOR")
    private Role role;
}
