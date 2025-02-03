package mate.academy.taskmanagementapp.service.comment;

import java.util.List;
import mate.academy.taskmanagementapp.dto.comment.CommentDto;
import mate.academy.taskmanagementapp.dto.comment.CreateCommentRequestDto;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    CommentDto createComment(Long userId, CreateCommentRequestDto createCommentRequestDto);

    List<CommentDto> getAllCommentsForTask(Long userId, Long taskId, Pageable pageable);
}
