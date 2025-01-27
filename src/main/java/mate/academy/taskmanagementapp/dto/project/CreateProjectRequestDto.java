package mate.academy.taskmanagementapp.dto.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import mate.academy.taskmanagementapp.model.Project;

public record CreateProjectRequestDto(
        @NotBlank(message = "name may not be blank")
        String name,
        String description,
        @NotNull
        LocalDate startDate,
        @NotNull
        LocalDate endDate) {
}
