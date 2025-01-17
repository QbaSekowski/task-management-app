package mate.academy.taskmanagementapp.service.attachment;

import java.io.IOException;
import java.util.List;
import mate.academy.taskmanagementapp.dto.attachment.AttachmentDto;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {
    AttachmentDto uploadAttachment(Long userId, Long taskId, MultipartFile multipartFile)
            throws IOException, InterruptedException;

    List<AttachmentDto> getAllAttachments(Long userId, Long taskId);
}
