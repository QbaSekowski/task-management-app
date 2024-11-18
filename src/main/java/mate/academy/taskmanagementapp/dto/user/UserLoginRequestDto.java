package mate.academy.taskmanagementapp.dto.user;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UserLoginRequestDto(
        @NotBlank(message = "username may not be blank")
        String username,
        @NotBlank(message = "password may not be blank")
        @Length(min = 8, max = 25)
        String password) {
}