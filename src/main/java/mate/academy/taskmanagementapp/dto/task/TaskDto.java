package mate.academy.taskmanagementapp.dto.task;

import java.time.LocalDateTime;
import java.util.Set;
import mate.academy.taskmanagementapp.dto.label.LabelDto;

public record TaskDto(
        Long id,
        String name,
        String description,
        String priority,
        String status,
        LocalDateTime dueDate,
        Long projectId,
        Long assigneeId,
        Set<LabelDto> labels) {
}
