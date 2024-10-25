package mate.academy.taskmanagementapp.mapper;

import mate.academy.taskmanagementapp.config.MapperConfig;
import mate.academy.taskmanagementapp.dto.attachment.AttachmentDto;
import mate.academy.taskmanagementapp.model.Attachment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface AttachmentMapper {
    @Mapping(source = "task.id", target = "taskId")
    AttachmentDto toDto(Attachment attachment);
}
