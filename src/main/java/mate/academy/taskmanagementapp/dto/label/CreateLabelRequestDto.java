package mate.academy.taskmanagementapp.dto.label;

import jakarta.validation.constraints.NotBlank;

public record CreateLabelRequestDto(
        @NotBlank(message = "name may not be blank")
        String name,
        @NotBlank(message = "color may not be blank")
        String color) {
}
