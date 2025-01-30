package mate.academy.taskmanagementapp.mapper;

import mate.academy.taskmanagementapp.config.MapperConfig;
import mate.academy.taskmanagementapp.dto.task.CreateTaskRequestDto;
import mate.academy.taskmanagementapp.dto.task.TaskDto;
import mate.academy.taskmanagementapp.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface TaskMapper {
    @Mapping(source = "projectId", target = "project.id")
    @Mapping(source = "assigneeId", target = "assignee.id")
    Task toModel(CreateTaskRequestDto createTaskRequestDto);

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "assignee.id", target = "assigneeId")
    TaskDto toDto(Task task);

    Task updateTask(CreateTaskRequestDto createTaskRequestDto, @MappingTarget Task task);
}
