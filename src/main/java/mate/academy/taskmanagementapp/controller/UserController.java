package mate.academy.taskmanagementapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mate.academy.taskmanagementapp.dto.user.UserRegistrationRequestDto;
import mate.academy.taskmanagementapp.dto.user.UserResponseDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
@Tag(name = "User management", description = "Endpoints for managing users")
public class UserController {
    //private final UserService userService;

    @PutMapping("/{id}/roles")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Update user's role", description = "Update user's role")
    public UserResponseDto updateUserRole(@PathVariable Long id,
                                          /*@RequestBody @Valid UpdateUserRoleDto updateUserRoleDto*/) {
        //return userService.updateUserRole(id, updateUserRoleDto);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Retrieve user's info", description = "Retrieve user's info")
    public UserResponseDto getUserInfo() {
        //return userService.getUserInfo(Id from logged user);
    }

    @PutMapping("/me")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update user's info", description = "Update user's info")
    public UserResponseDto updateUserInfo(
            @RequestBody UserRegistrationRequestDto userRegistrationRequestDto) {
        //return userService.updateUserInfo(Id from logged user, userRegistrationRequestDto);
    }
}
