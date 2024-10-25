package mate.academy.taskmanagementapp.mapper;

import mate.academy.taskmanagementapp.config.MapperConfig;
import mate.academy.taskmanagementapp.dto.label.LabelDto;
import mate.academy.taskmanagementapp.model.Label;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface LabelMapper {
    Label toModel(LabelDto labelDto);

    LabelDto toDto(Label label);
}
