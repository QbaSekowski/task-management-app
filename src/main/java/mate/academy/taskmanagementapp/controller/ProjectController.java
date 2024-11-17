package mate.academy.taskmanagementapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.taskmanagementapp.dto.project.CreateProjectRequestDto;
import mate.academy.taskmanagementapp.dto.project.ProjectDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
@RequestMapping(value = "api/projects")
@Tag(name = "Project management", description = "Endpoints for managing projects")
public class ProjectController {
    //private final ProjectService projectService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new project", description = "Create a new project "
            + "providing name, description, startDate, endDate and status")
    public ProjectDto createProject(CreateProjectRequestDto createProjectRequestDto) {
        // return projectService.createProject(createProjectRequestDto);
    }

    @GetMapping
    @Operation(summary = "Get all projects", description = "Get all user's projects")
    public List<ProjectDto> getAllProjects(Pageable pageable) {
        //return projectService.getAllProjects(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get project's details", description = "Get project's details providing its Id")
    public ProjectDto getProjectDetails(@PathVariable Long id) {
        //return projectService.getProjectDetails(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update project", description = "Update project "
            + "providing name, description, startDate, endDate and status")
    public ProjectDto updateProject(@PathVariable Long id,
                                    @RequestBody CreateProjectRequestDto createProjectRequestDto) {
        //return projectService.updateProject(id, createProjectRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete project", description = "Delete project with given Id")
    public void deleteProject(@PathVariable Long id) {
        //projectService.deleteProject(id);
    }
}
