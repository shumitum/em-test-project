package com.effectivemobile.testproject.user.dto;

import com.effectivemobile.testproject.user.Role;
import com.effectivemobile.testproject.user.UserPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewUserDto {
    private Integer id;
    private String name;
    private UserPosition userPosition;
    private Role role;
}
