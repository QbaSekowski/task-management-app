package mate.academy.taskmanagementapp.dto.user;

import jakarta.validation.constraints.NotNull;
import mate.academy.taskmanagementapp.model.Role;

public record UserRoleUpdateDto(
        @NotNull
        Role.RoleName name) {
}
