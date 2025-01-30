package mate.academy.taskmanagementapp.service.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import mate.academy.taskmanagementapp.dto.project.CreateProjectRequestDto;
import mate.academy.taskmanagementapp.dto.project.ProjectDto;
import mate.academy.taskmanagementapp.mapper.ProjectMapper;
import mate.academy.taskmanagementapp.mapper.UserMapper;
import mate.academy.taskmanagementapp.model.Project;
import mate.academy.taskmanagementapp.model.User;
import mate.academy.taskmanagementapp.repository.project.ProjectRepository;
import mate.academy.taskmanagementapp.repository.user.UserRepository;
import mate.academy.taskmanagementapp.service.project.impl.ProjectServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProjectMapper projectMapper;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private ProjectServiceImpl projectService;

    @Test
    @DisplayName("Create project")
    public void createProject_CorrectProjectRequest_ReturnsProjectDto() {
        User user = createUser();
        Project newProject = createProject();
        ProjectDto expected = new ProjectDto(newProject.getId(), newProject.getName(),
                newProject.getDescription(), newProject.getStartDate(),
                newProject.getEndDate(), newProject.getStatus(), newProject.getUsers().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toSet()));
        CreateProjectRequestDto createProjectRequestDto =
                new CreateProjectRequestDto(newProject.getName(), newProject.getDescription(),
                        newProject.getStartDate(), newProject.getEndDate());
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(projectMapper.toDto(newProject)).thenReturn(expected);
        when(projectMapper.toModel(createProjectRequestDto)).thenReturn(newProject);
        when(projectRepository.save(newProject)).thenReturn(newProject);
        ProjectDto actual = projectService.createProject(user.getId(), createProjectRequestDto);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Retrieve project details")
    public void getProjectDetails_CorrectId_ReturnsProjectDto() {
        User user = createUser();
        Project project = createProject();
        ProjectDto expected = new ProjectDto(project.getId(), project.getName(),
                project.getDescription(), project.getStartDate(),
                project.getEndDate(), project.getStatus(), project.getUsers().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toSet()));
        when(projectRepository.findByUserIdAndProjectId(user.getId(), project.getId()))
                .thenReturn(Optional.of(project));
        when(projectMapper.toDto(project)).thenReturn(expected);
        ProjectDto actual = projectService.getProjectDetails(user.getId(), project.getId());
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Delete project")
    public void deleteProject_CorrectId_ProjectDeleted() {
        User user = createUser();
        Project project = createProject();
        when(projectRepository.findByUserIdAndProjectId(user.getId(), project.getId()))
                .thenReturn(Optional.ofNullable(project));
        projectService.deleteProject(user.getId(), project.getId());
        verify(projectRepository, times(1)).deleteById(project.getId());
    }

    private Project createProject() {
        Project project = new Project();
        project.setId(1L);
        project.setName("Test Project");
        project.setDescription("Test Description");
        project.setStartDate(LocalDate.parse("2025-01-12"));
        project.setEndDate(LocalDate.parse("2025-01-13"));
        project.setStatus(Project.Status.INITIATED);
        User user = createUser();
        project.setUsers(Set.of(user));
        return project;
    }

    private User createUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("User Name");
        user.setPassword("Password");
        user.setEmail("email@email.com");
        user.setFirstName("First Name");
        user.setLastName("Last Name");
        return user;
    }
}
