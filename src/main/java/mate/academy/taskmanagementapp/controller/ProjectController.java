package mate.academy.taskmanagementapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.taskmanagementapp.dto.project.CreateProjectRequestDto;
import mate.academy.taskmanagementapp.dto.project.ProjectDto;
import mate.academy.taskmanagementapp.service.project.ProjectService;
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
@RequestMapping(value = "/api/projects")
@Tag(name = "Project management",
        description = "Endpoints for managing projects")
public class ProjectController {
    private final ProjectService projectService;
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Create a new project",
            description = "Create a new project "
            + "by providing name, description, startDate, endDate and status")
    public ProjectDto createProject(Authentication authentication,
                                    @RequestBody @Valid CreateProjectRequestDto
                                            createProjectRequestDto) {
        return projectService.createProject(
                userService.getUserFromAuthentication(authentication).getId(),
                createProjectRequestDto);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all projects",
            description = "Get all user's projects")
    public List<ProjectDto> getAllProjects(Authentication authentication, Pageable pageable) {
        return projectService.getAllProjects(
                userService.getUserFromAuthentication(authentication).getId(), pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get project's details",
            description = "Get project's details by providing its Id")
    public ProjectDto getProjectDetails(Authentication authentication, @PathVariable Long id) {
        return projectService.getProjectDetails(
                userService.getUserFromAuthentication(authentication).getId(), id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Update project",
            description = "Update project by providing name, "
                    + "description, startDate, endDate and status")
    public ProjectDto updateProject(Authentication authentication,
                                    @PathVariable Long id,
                                    @RequestBody @Valid CreateProjectRequestDto
                                                createProjectRequestDto) {
        return projectService.updateProject(
                userService.getUserFromAuthentication(authentication).getId(), id,
                createProjectRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Delete project",
            description = "Delete project with given Id")
    public void deleteProject(Authentication authentication, @PathVariable Long id) {
        projectService.deleteProject(
                userService.getUserFromAuthentication(authentication).getId(), id);
    }
}
