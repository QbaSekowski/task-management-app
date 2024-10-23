package mate.academy.taskmanagementapp.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCommentRequestDto(
        @NotNull
        Long taskId,
        @NotBlank(message = "text may not be blank")
        String text) {
}
