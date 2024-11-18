package mate.academy.taskmanagementapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mate.academy.taskmanagementapp.dto.user.UserRegistrationRequestDto;
import mate.academy.taskmanagementapp.dto.user.UserResponseDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
@Tag(name = "User management", description = "Endpoints for managing users")
public class UserController {
    //private final UserService userService;

    @PutMapping("/{id}/roles")
    @Operation(summary = "Update user's role", description = "Update user's role")
    public UserResponseDto updateUserRole(@PathVariable Long id,
                                          /*@RequestBody @Valid UpdateUserRoleDto updateUserRoleDto*/) {
        //return userService.updateUserRole(id, updateUserRoleDto);
    }

    @GetMapping("/me")
    @Operation(summary = "Retrieve user's info", description = "Retrieve user's info")
    public UserResponseDto getUserInfo() {
        //return userService.getUserInfo(Id from logged user);
    }

    @PutMapping("/me")
    @Operation(summary = "Update user's info", description = "Update user's info")
    public UserResponseDto updateUserInfo(
            @RequestBody UserRegistrationRequestDto userRegistrationRequestDto) {
        //return userService.updateUserInfo(Id from logged user, userRegistrationRequestDto);
    }
}
