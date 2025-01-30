package mate.academy.taskmanagementapp.mapper;

import mate.academy.taskmanagementapp.config.MapperConfig;
import mate.academy.taskmanagementapp.dto.user.UserRegistrationRequestDto;
import mate.academy.taskmanagementapp.dto.user.UserResponseDto;
import mate.academy.taskmanagementapp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    User toModel(UserRegistrationRequestDto userRegistrationRequestDto);

    User updateUser(UserRegistrationRequestDto userRegistrationRequestDto,
                    @MappingTarget User user);
}
