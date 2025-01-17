package mate.academy.taskmanagementapp.service.attachment.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.taskmanagementapp.dto.attachment.AttachmentDto;
import mate.academy.taskmanagementapp.exception.EntityNotFoundException;
import mate.academy.taskmanagementapp.mapper.AttachmentMapper;
import mate.academy.taskmanagementapp.model.Attachment;
import mate.academy.taskmanagementapp.model.Task;
import mate.academy.taskmanagementapp.repository.attachment.AttachmentRepository;
import mate.academy.taskmanagementapp.repository.task.TaskRepository;
import mate.academy.taskmanagementapp.service.attachment.AttachmentService;
import mate.academy.taskmanagementapp.service.dropbox.DropboxService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final AttachmentMapper attachmentMapper;
    private final DropboxService dropboxService;
    private final TaskRepository taskRepository;

    @Transactional
    @Override
    public AttachmentDto uploadAttachment(Long userId, Long taskId, MultipartFile multipartFile)
            throws IOException, InterruptedException {
        Task task = taskRepository.findByAssigneeIdAndId(userId, taskId).orElseThrow(
                () -> new EntityNotFoundException("Task by id " + taskId
                        + " and user id " + userId + " not found"));
        Attachment attachment = new Attachment();
        attachment.setTask(task);
        attachment.setFileName(multipartFile.getOriginalFilename());
        String dropboxId = dropboxService.uploadFile(multipartFile);
        attachment.setDropboxId(dropboxId);
        return attachmentMapper.toDto(attachmentRepository.save(attachment));
    }

    @Override
    public List<AttachmentDto> getAllAttachments(Long userId, Long taskId) {
        Task task = taskRepository.findByAssigneeIdAndId(userId, taskId).orElseThrow(
                () -> new EntityNotFoundException("Task by id " + taskId
                        + " and user id " + userId + " not found"));
        return attachmentRepository.findAllByTaskId(task.getId()).stream()
                .map(attachmentMapper::toDto)
                .collect(Collectors.toList());
    }
}
