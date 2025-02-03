package mate.academy.taskmanagementapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.taskmanagementapp.dto.comment.CommentDto;
import mate.academy.taskmanagementapp.dto.comment.CreateCommentRequestDto;
import mate.academy.taskmanagementapp.service.comment.CommentService;
import mate.academy.taskmanagementapp.service.user.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/comments")
@Tag(name = "Comment management",
        description = "Endpoints for managing comments")
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create a new comment",
            description = "Create a new comment "
            + "by providing text for a task")
    public CommentDto createComment(Authentication authentication,
                                    @RequestBody @Valid CreateCommentRequestDto
                                            createCommentRequestDto) {
        return commentService.createComment(
                userService.getUserFromAuthentication(authentication).getId(),
                createCommentRequestDto);
    }

    @GetMapping("/{taskId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get comments for a task",
            description = "Get all comments for a task")
    public List<CommentDto> getAllCommentsForTask(Authentication authentication,
                                                  @PathVariable Long taskId,
                                                  Pageable pageable) {
        return commentService.getAllCommentsForTask(
                userService.getUserFromAuthentication(authentication).getId(),
                taskId, pageable);
    }
}
