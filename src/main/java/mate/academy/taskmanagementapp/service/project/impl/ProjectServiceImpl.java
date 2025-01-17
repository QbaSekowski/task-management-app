package mate.academy.taskmanagementapp.service.project.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.taskmanagementapp.dto.project.CreateProjectRequestDto;
import mate.academy.taskmanagementapp.dto.project.ProjectDto;
import mate.academy.taskmanagementapp.exception.EntityNotFoundException;
import mate.academy.taskmanagementapp.mapper.ProjectMapper;
import mate.academy.taskmanagementapp.model.Project;
import mate.academy.taskmanagementapp.model.User;
import mate.academy.taskmanagementapp.repository.project.ProjectRepository;
import mate.academy.taskmanagementapp.repository.user.UserRepository;
import mate.academy.taskmanagementapp.service.project.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;

    @Transactional
    @Override
    public ProjectDto createProject(Long userId,
                                    CreateProjectRequestDto createProjectRequestDto) {
        Project project = projectMapper.toModel(createProjectRequestDto);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User with id " + userId + " not found"));
        project.setUsers(Set.of(user));
        project.setStatus(Project.Status.INITIATED);
        return projectMapper.toDto(projectRepository.save(project));
    }

    @Override
    public List<ProjectDto> getAllProjects(Long userId) {
        return projectRepository.findAllByUserId(userId).stream()
                .map(projectMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectDto getProjectDetails(Long userId, Long projectId) {
        return projectMapper.toDto(projectRepository.findByUserIdAndProjectId(userId, projectId).orElseThrow(
                () -> new EntityNotFoundException("Project by id "
                        + projectId + " and user id " + userId + " not found")));
    }

    @Transactional
    @Override
    public ProjectDto updateProject(Long userId, Long projectId,
                                    CreateProjectRequestDto createProjectRequestDto) {
        Project project = projectRepository.findByUserIdAndProjectId(userId, projectId).orElseThrow(
                () -> new EntityNotFoundException("Project by id "
                        + projectId + " and user id " + userId + " not found"));
        project.setId(projectId);
        return projectMapper.toDto(projectRepository.save(project));
    }

    @Transactional
    @Override
    public void deleteProject(Long userId, Long projectId) {
        Project project = projectRepository.findByUserIdAndProjectId(userId, projectId).orElseThrow(
                () -> new EntityNotFoundException("Project by id "
                        + projectId + " and user id " + userId + " not found"));
        projectRepository.deleteById(project.getId());
    }
}
