package mate.academy.taskmanagementapp.service.user.impl;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.taskmanagementapp.dto.user.UserRegistrationRequestDto;
import mate.academy.taskmanagementapp.dto.user.UserResponseDto;
import mate.academy.taskmanagementapp.dto.user.UserRoleUpdateDto;
import mate.academy.taskmanagementapp.exception.EntityNotFoundException;
import mate.academy.taskmanagementapp.exception.RegistrationException;
import mate.academy.taskmanagementapp.mapper.UserMapper;
import mate.academy.taskmanagementapp.model.Role;
import mate.academy.taskmanagementapp.model.User;
import mate.academy.taskmanagementapp.repository.role.RoleRepository;
import mate.academy.taskmanagementapp.repository.user.UserRepository;
import mate.academy.taskmanagementapp.service.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Role.RoleName DEFAULT_ROLE_NAME = Role.RoleName.USER;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void updateUserRole(Long id, UserRoleUpdateDto userRoleUpdateDto) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with id " + id + " not found"));
        Role role = roleRepository.findRoleByName(userRoleUpdateDto.name()).orElseThrow(
                () -> new EntityNotFoundException("Role with name "
                        + userRoleUpdateDto.name() + " not found"));
        user.getRoles().add(role);
        userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto getUserInfo(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with id " + id + " not found"));
        return userMapper.toDto(user);
    }

    @Transactional
    @Override
    public UserResponseDto updateUserInfo(Long id,
                                          UserRegistrationRequestDto userRegistrationRequestDto) {
        User user = userMapper.toModel(userRegistrationRequestDto);
        user.setId(id);
        return userMapper.toDto(userRepository.save(user));
    }

    @Transactional
    @Override
    public UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException {
        if (userRepository.findUserByEmail(userRegistrationRequestDto.email()).isPresent()) {
            throw new RegistrationException("Email address already in use");
        }
        User user = userMapper.toModel(userRegistrationRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role defaultRole = roleRepository.findRoleByName(DEFAULT_ROLE_NAME)
                .orElseThrow(() -> new RegistrationException("Can't find default role"));
        user.setRoles(Set.of(defaultRole));
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public User getUserFromAuthentication(Authentication authentication) {
        return (User) authentication.getPrincipal();
    }
}
