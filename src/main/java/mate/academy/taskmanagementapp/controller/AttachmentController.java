package mate.academy.taskmanagementapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.taskmanagementapp.dto.attachment.AttachmentDto;
import mate.academy.taskmanagementapp.dto.comment.CreateCommentRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/attachments")
@Tag(name = "Attachment management", description = "Endpoints for managing attachments")
public class AttachmentController {
    //private final AttachmentService attachmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Upload a new attachment", description = "Upload a new attachment "
            + "providing a file for the task")
    public AttachmentDto uploadFile(@RequestParam("taskId") Long taskId,
                                    @RequestParam("file") MultipartFile file) {
        //return attachmentService.uploadFile(taskId, file);
    }

    @GetMapping
    @Operation(summary = "Get attachments for a task", description = "Get all attachments for a task")
    public List<AttachmentDto> getAllAttachments(@RequestParam("taskId") Long taskId) {
        //return attachmentService.getAllAttachments(taskId);
    }
}
