package mate.academy.taskmanagementapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.taskmanagementapp.dto.task.CreateTaskRequestDto;
import mate.academy.taskmanagementapp.dto.task.TaskDto;
import mate.academy.taskmanagementapp.model.User;
import mate.academy.taskmanagementapp.service.email.EmailService;
import mate.academy.taskmanagementapp.service.task.TaskService;
import mate.academy.taskmanagementapp.service.user.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/tasks")
@Tag(name = "Task management", description = "Endpoints for managing tasks")
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;
    private final EmailService emailService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Create a new task",
            description = "Create a new task "
            + "providing name, description, priority, "
            + "status, dueDate, projectId and assigneeId")
    public TaskDto createTask(Authentication authentication,
                              @RequestBody @Valid CreateTaskRequestDto createTaskRequestDto) {
        User user = userService.getUserFromAuthentication(authentication);
        TaskDto taskDto = taskService.createTask(user.getId(), createTaskRequestDto);
        if (taskDto != null) {
            emailService.sendEmail(user.getEmail(), "Task created successfully",
                    "Task has been created successfully. Its deadline is "
                            + taskDto.dueDate());
        }
        return taskDto;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all tasks",
            description = "Retrieve all tasks of a specific user")
    public List<TaskDto> getAllTasks(Authentication authentication, Pageable pageable) {
        return taskService.getAllTasks(
                userService.getUserFromAuthentication(authentication).getId(), pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Retrieve task details",
            description = "Retrieve task details by providing its Id")
    public TaskDto getTaskById(Authentication authentication, @PathVariable Long id) {
        return taskService.getTaskById(
                userService.getUserFromAuthentication(authentication).getId(), id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Update task",
            description = "Update task by providing its ID")
    public TaskDto updateTask(Authentication authentication,
                              @PathVariable Long id,
                              @RequestBody @Valid CreateTaskRequestDto createTaskRequestDto) {
        return taskService.updateTask(
                userService.getUserFromAuthentication(authentication).getId(),
                id, createTaskRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Delete task",
            description = "Delete task by providing its ID")
    public void deleteTask(Authentication authentication, @PathVariable Long id) {
        taskService.deleteTask(
                userService.getUserFromAuthentication(authentication).getId(), id);
    }
}
