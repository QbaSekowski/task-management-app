package mate.academy.taskmanagementapp.dto.project;

import java.time.LocalDateTime;
import java.util.Set;
import mate.academy.taskmanagementapp.dto.user.UserResponseDto;
import mate.academy.taskmanagementapp.model.Project;

public record ProjectDto(
        Long id,
        String name,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Project.Status status,
        Set<UserResponseDto> users) {
}
