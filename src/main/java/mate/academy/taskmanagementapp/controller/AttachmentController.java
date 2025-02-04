package mate.academy.taskmanagementapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.taskmanagementapp.dto.attachment.AttachmentDto;
import mate.academy.taskmanagementapp.model.User;
import mate.academy.taskmanagementapp.service.attachment.AttachmentService;
import mate.academy.taskmanagementapp.service.email.EmailService;
import mate.academy.taskmanagementapp.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/attachments")
@Tag(name = "Attachment management", description = "Endpoints for managing attachments")
public class AttachmentController {
    private final AttachmentService attachmentService;
    private final UserService userService;
    private final EmailService emailService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Upload a new attachment",
            description = "Upload a new attachment "
            + "by providing a file for the task")
    public AttachmentDto uploadAttachment(Authentication authentication,
                                          @RequestParam("taskId") Long taskId,
                                          @RequestParam("multipartFile") MultipartFile
                                                      multipartFile)
            throws IOException, InterruptedException {
        User user = userService.getUserFromAuthentication(authentication);
        AttachmentDto attachmentDto = attachmentService.uploadAttachment(
                user.getId(),
                taskId, multipartFile);
        if (attachmentDto != null) {
            emailService.sendEmail(user.getEmail(), "Upload successful",
                    "Your attachment for task id: " + taskId
                            + " was uploaded successfully to Dropbox.");
        }
        return attachmentDto;
    }

    @GetMapping("/{taskId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get attachments for a task",
            description = "Get all attachments for a task")
    public List<AttachmentDto> getAllAttachments(Authentication authentication,
                                                 @PathVariable Long taskId) {
        return attachmentService.getAllAttachments(
                userService.getUserFromAuthentication(authentication).getId(), taskId);
    }
}
