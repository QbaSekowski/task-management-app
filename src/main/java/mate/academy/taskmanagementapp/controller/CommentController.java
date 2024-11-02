package mate.academy.taskmanagementapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.taskmanagementapp.dto.comment.CommentDto;
import mate.academy.taskmanagementapp.dto.comment.CreateCommentRequestDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/comments")
@Tag(name = "Comment management", description = "Endpoints for managing comments")
public class CommentController {
    //private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new comment", description = "Create a new comment "
            + "providing text for a task")
    public CommentDto createComment(@RequestBody @Valid CreateCommentRequestDto createCommentRequestDto) {
        //return commentService.create(createCommentRequestDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get comments for a task", description = "Get all comments for a task")
    public List<CommentDto> getAllComments(@PathVariable Long taskId, Pageable pageable) {
        //return commentService.getAllComments(taskId, pageable);
    }
}
