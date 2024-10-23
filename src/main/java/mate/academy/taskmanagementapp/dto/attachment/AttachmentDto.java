package mate.academy.taskmanagementapp.dto.attachment;

import java.time.LocalDateTime;

public record AttachmentDto(
        Long id,
        Long taskId,
        String dropboxId,
        String fileName,
        LocalDateTime uploadDate) {
}
