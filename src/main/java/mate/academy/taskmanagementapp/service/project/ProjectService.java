package mate.academy.taskmanagementapp.service.project;

import java.util.List;
import mate.academy.taskmanagementapp.dto.project.CreateProjectRequestDto;
import mate.academy.taskmanagementapp.dto.project.ProjectDto;

public interface ProjectService {
    ProjectDto createProject(Long userId, CreateProjectRequestDto createProjectRequestDto);

    List<ProjectDto> getAllProjects(Long userId);

    ProjectDto getProjectDetails(Long userId, Long projectId);

    ProjectDto updateProject(Long userId, Long projectId,
                             CreateProjectRequestDto createProjectRequestDto);

    void deleteProject(Long userId, Long projectId);
}
