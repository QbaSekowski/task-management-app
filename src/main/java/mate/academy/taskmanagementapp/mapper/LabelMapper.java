package mate.academy.taskmanagementapp.mapper;

import mate.academy.taskmanagementapp.config.MapperConfig;
import mate.academy.taskmanagementapp.dto.label.CreateLabelRequestDto;
import mate.academy.taskmanagementapp.dto.label.LabelDto;
import mate.academy.taskmanagementapp.model.Label;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface LabelMapper {
    Label toModel(CreateLabelRequestDto createLabelRequestDto);

    LabelDto toDto(Label label);

    Label updateLabel(CreateLabelRequestDto createLabelRequestDto, @MappingTarget Label label);
}
