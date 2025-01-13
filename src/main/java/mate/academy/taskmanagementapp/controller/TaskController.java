package mate.academy.taskmanagementapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.awt.print.Pageable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.taskmanagementapp.dto.task.CreateTaskRequestDto;
import mate.academy.taskmanagementapp.dto.task.TaskDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/tasks")
@Tag(name = "Task management", description = "Endpoints for managing tasks")
public class TaskController {
    //private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Create a new task", description = "Create a new task "
            + "providing name, description, priority, "
            + "status, dueDate, projectId and assigneeId")
    public TaskDto createTask(@RequestBody @Valid CreateTaskRequestDto createTaskRequestDto) {
        //return taskService.createTask(createTaskRequestDto);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all tasks", description = "Retrieve all tasks")
    public List<TaskDto> getAllTasks(@RequestParam("projectId") Long projectId,
                                            Pageable pageable) {
        //return taskService.getAllProjectTasks(projectId, pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Retrieve task details", description = "Retrieve task details by providing its Id")
    public TaskDto getTaskById(@PathVariable Long id) {
        //return taskService.getTaskById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Update task", description = "Update task by providing its ID")
    public TaskDto updateTask(@PathVariable Long id,
                              @RequestBody @Valid CreateTaskRequestDto createTaskRequestDto) {
        //return taskService.updateTask(id, createTaskRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Delete task", description = "Delete task by providing its ID")
    public void deleteTask(@PathVariable Long id) {
        //taskService.deleteTask(id);
    }
}
