package com.effectivemobile.testproject.user;

import com.effectivemobile.testproject.user.dto.ViewUserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    ViewUserDto toViewUserDto(User user);
}
