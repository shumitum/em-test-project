package com.effectivemobile.testproject.task;

import com.effectivemobile.testproject.task.dto.CreateTaskDto;
import com.effectivemobile.testproject.task.dto.ViewTaskDto;
import com.effectivemobile.testproject.user.UserMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface TaskMapper {

    ViewTaskDto toViewTaskDto(Task task);

    Task toTask(CreateTaskDto createTaskDto);


}
