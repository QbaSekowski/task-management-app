package mate.academy.taskmanagementapp.service.comment.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.taskmanagementapp.dto.comment.CommentDto;
import mate.academy.taskmanagementapp.dto.comment.CreateCommentRequestDto;
import mate.academy.taskmanagementapp.exception.EntityNotFoundException;
import mate.academy.taskmanagementapp.mapper.CommentMapper;
import mate.academy.taskmanagementapp.model.Comment;
import mate.academy.taskmanagementapp.model.Task;
import mate.academy.taskmanagementapp.model.User;
import mate.academy.taskmanagementapp.repository.comment.CommentRepository;
import mate.academy.taskmanagementapp.repository.task.TaskRepository;
import mate.academy.taskmanagementapp.repository.user.UserRepository;
import mate.academy.taskmanagementapp.service.comment.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public CommentDto createComment(Long userId, CreateCommentRequestDto createCommentRequestDto) {
        Comment comment = commentMapper.toModel(createCommentRequestDto);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User with id " + userId + " not found"));
        Task task = taskRepository.findByAssigneeIdAndId(userId, createCommentRequestDto.taskId()).orElseThrow(
                () -> new EntityNotFoundException("User with id " + userId + " not found or task with id "
                        + createCommentRequestDto.taskId() + " not found"));
        comment.setUser(user);
        comment.setTask(task);
        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public List<CommentDto> getAllCommentsForTask(Long taskId) {
        return commentRepository.findAllByTaskId(taskId).stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }
}
