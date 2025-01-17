package mate.academy.taskmanagementapp.service.user;

import mate.academy.taskmanagementapp.dto.user.UserRegistrationRequestDto;
import mate.academy.taskmanagementapp.dto.user.UserResponseDto;
import mate.academy.taskmanagementapp.dto.user.UserRoleUpdateDto;
import mate.academy.taskmanagementapp.exception.RegistrationException;
import mate.academy.taskmanagementapp.model.User;
import org.springframework.security.core.Authentication;

public interface UserService {
    void updateUserRole(Long id, UserRoleUpdateDto userRoleUpdateDto);

    UserResponseDto getUserInfo(Long id);

    UserResponseDto updateUserInfo(Long id, UserRegistrationRequestDto userRegistrationRequestDto);

    UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException;

    User getUserFromAuthentication(Authentication authentication);
}
