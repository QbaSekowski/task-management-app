package mate.academy.taskmanagementapp.service.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import mate.academy.taskmanagementapp.mapper.ProjectMapper;
import mate.academy.taskmanagementapp.model.Project;
import mate.academy.taskmanagementapp.model.User;
import mate.academy.taskmanagementapp.repository.project.ProjectRepository;
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
    private ProjectMapper projectMapper;
    @InjectMocks
    private ProjectServiceImpl projectService;

    @Test
    @DisplayName("Delete project")
    public void deleteProject_CorrectId_ProjectDeleted() {
        Project project = createProject();
        when(projectRepository.findByUserIdAndProjectId(1L, 1L))
                .thenReturn(Optional.ofNullable(project));
        projectService.deleteProject(1L, 1L);
        verify(projectRepository, times(1)).deleteById(1L);
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
