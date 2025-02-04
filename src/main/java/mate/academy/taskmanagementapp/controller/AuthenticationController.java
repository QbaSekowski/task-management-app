package mate.academy.taskmanagementapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.taskmanagementapp.dto.user.UserLoginRequestDto;
import mate.academy.taskmanagementapp.dto.user.UserLoginResponseDto;
import mate.academy.taskmanagementapp.dto.user.UserRegistrationRequestDto;
import mate.academy.taskmanagementapp.dto.user.UserResponseDto;
import mate.academy.taskmanagementapp.exception.RegistrationException;
import mate.academy.taskmanagementapp.security.AuthenticationService;
import mate.academy.taskmanagementapp.service.email.EmailService;
import mate.academy.taskmanagementapp.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/auth")
@Tag(name = "Authentication management",
        description = "Endpoints for managing authentication")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final EmailService emailService;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new user",
            description = "Create a new user with given username, "
            + " password, email, first name, last name")
    public UserResponseDto register(
            @RequestBody @Valid UserRegistrationRequestDto request)
            throws RegistrationException {
        UserResponseDto userResponseDto = userService.register(request);
        if (userResponseDto != null) {
            emailService.sendEmail(userResponseDto.email(), "Registration successfull",
                    "Your registration was successfull. "
                    + "Now you can log in application using credentials "
                    + "you provided during registration");
        }
        return userResponseDto;
    }

    @PostMapping("/login")
    @Operation(summary = "Log in the user",
            description = "Log registered user into the service "
            + "using email and password")
    public UserLoginResponseDto login(
            @RequestBody @Valid UserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }
}
