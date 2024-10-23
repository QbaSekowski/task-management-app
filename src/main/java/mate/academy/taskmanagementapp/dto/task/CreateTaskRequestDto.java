package mate.academy.taskmanagementapp.dto.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;
import mate.academy.taskmanagementapp.model.Label;
import mate.academy.taskmanagementapp.model.Task;

public record CreateTaskRequestDto(
        @NotBlank(message = "name may not be blank")
        String name,
        String description,
        @NotNull
        Task.Priority priority,
        @NotNull
        Task.Status status,
        @NotNull
        LocalDateTime dueDate,
        @NotNull
        Long projectId,
        @NotNull
        Long assigneeId,
        Set<Label> labels) {
}
