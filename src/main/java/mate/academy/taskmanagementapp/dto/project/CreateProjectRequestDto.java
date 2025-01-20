package mate.academy.taskmanagementapp.dto.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import mate.academy.taskmanagementapp.model.Project;

public record CreateProjectRequestDto(
        @NotBlank(message = "name may not be blank")
        String name,
        String description,
        @NotNull
        LocalDateTime startDate,
        @NotNull
        LocalDateTime endDate,
        @NotNull
        Project.Status status) {
}
