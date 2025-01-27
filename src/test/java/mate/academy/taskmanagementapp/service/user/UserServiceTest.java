package mate.academy.taskmanagementapp.service.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;
import mate.academy.taskmanagementapp.dto.user.UserRegistrationRequestDto;
import mate.academy.taskmanagementapp.dto.user.UserResponseDto;
import mate.academy.taskmanagementapp.exception.RegistrationException;
import mate.academy.taskmanagementapp.mapper.UserMapper;
import mate.academy.taskmanagementapp.model.Role;
import mate.academy.taskmanagementapp.model.User;
import mate.academy.taskmanagementapp.repository.role.RoleRepository;
import mate.academy.taskmanagementapp.repository.user.UserRepository;
import mate.academy.taskmanagementapp.service.user.impl.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Get user info")
    public void getUserInfo_CorrectId_ReturnsUserDto() {
        User user = createUser();
        UserResponseDto expectedUserResponseDto = createUserResponseDto();
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(expectedUserResponseDto);
        UserResponseDto actualUserResponseDto = userService.getUserInfo(user.getId());
        assertEquals(expectedUserResponseDto, actualUserResponseDto);
    }

    @Test
    @DisplayName("Register new user")
    public void registerNewUser_CorrectUserRequest_ReturnsUserDto() throws RegistrationException {
        User newUser = createUser();
        Role role = new Role();
        role.setId(1L);
        role.setName(Role.RoleName.USER);
        newUser.setRoles(Set.of(role));
        UserResponseDto expectedUserResponseDto = createUserResponseDto();
        UserRegistrationRequestDto userRegistrationRequestDto
                = new UserRegistrationRequestDto(newUser.getUsername(), newUser.getPassword(),
                newUser.getPassword(), newUser.getEmail(), newUser.getFirstName(),
                newUser.getLastName());
        when(userMapper.toModel(userRegistrationRequestDto)).thenReturn(newUser);
        when(passwordEncoder.encode(newUser.getPassword())).thenReturn(newUser.getPassword());
        when(userRepository.save(newUser)).thenReturn(newUser);
        when(roleRepository.findRoleByName(role.getName())).thenReturn(Optional.of(role));
        when(userMapper.toDto(newUser)).thenReturn(expectedUserResponseDto);
        UserResponseDto actualUserResponseDto = userService.register(userRegistrationRequestDto);
        assertNotNull(actualUserResponseDto);
        assertEquals(expectedUserResponseDto, actualUserResponseDto);
    }

    private User createUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email@email.com");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        return user;
    }

    private UserResponseDto createUserResponseDto() {
        return new UserResponseDto(1L, "username",
                "email@email.com", "firstName", "lastName");
    }
}
