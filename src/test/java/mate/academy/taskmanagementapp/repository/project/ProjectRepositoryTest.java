package mate.academy.taskmanagementapp.repository.project;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import mate.academy.taskmanagementapp.model.Project;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProjectRepositoryTest {
    @Autowired
    private ProjectRepository projectRepository;

    @Test
    @DisplayName("Find all projects by given user id")
    @Sql(scripts = "classpath:/database/project/add-three-projects.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/database/project/remove-all-projects.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllProjectsByUserId_CorrectId_ReturnsAllCorrectProjects() {
        List<Project> expectedProjects = createThreeProjects();
        List<Project> actualProjects = projectRepository.findAllByUserId(1L, PageRequest.of(0, 10));
        assertNotNull(actualProjects);
        assertEquals(expectedProjects.size(), actualProjects.size());
        assertEquals(expectedProjects.get(0).getId(), actualProjects.get(0).getId());
        assertEquals(expectedProjects.get(0).getName(), actualProjects.get(0).getName());
        assertEquals(expectedProjects.get(0).getDescription(),
                actualProjects.get(0).getDescription());
        assertEquals(expectedProjects.get(0).getStartDate(), actualProjects.get(0).getStartDate());
        assertEquals(expectedProjects.get(0).getEndDate(), actualProjects.get(0).getEndDate());
        assertEquals(expectedProjects.get(0).getStatus(), actualProjects.get(0).getStatus());
        assertEquals(expectedProjects.get(1).getId(), actualProjects.get(1).getId());
        assertEquals(expectedProjects.get(1).getName(), actualProjects.get(1).getName());
        assertEquals(expectedProjects.get(1).getDescription(),
                actualProjects.get(1).getDescription());
        assertEquals(expectedProjects.get(1).getStartDate(), actualProjects.get(1).getStartDate());
        assertEquals(expectedProjects.get(1).getEndDate(), actualProjects.get(1).getEndDate());
        assertEquals(expectedProjects.get(1).getStatus(), actualProjects.get(1).getStatus());
        assertEquals(expectedProjects.get(2).getId(), actualProjects.get(2).getId());
        assertEquals(expectedProjects.get(2).getName(), actualProjects.get(2).getName());
        assertEquals(expectedProjects.get(2).getDescription(),
                actualProjects.get(2).getDescription());
        assertEquals(expectedProjects.get(2).getStartDate(), actualProjects.get(2).getStartDate());
        assertEquals(expectedProjects.get(2).getEndDate(), actualProjects.get(2).getEndDate());
        assertEquals(expectedProjects.get(2).getStatus(), actualProjects.get(2).getStatus());
    }

    @Test
    @DisplayName("Find all projects by given user id and project id")
    @Sql(scripts = "classpath:/database/project/add-three-projects.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/database/project/remove-all-projects.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllProjectsByIdAndUserId_CorrectIds_ReturnsCorrectProject() {
        Optional<Project> expectedProjects = createProject();
        Optional<Project> actualProjects = projectRepository.findByUserIdAndProjectId(2L, 2L);
        assertNotNull(actualProjects);
        assertEquals(expectedProjects.get().getId(), actualProjects.get().getId());
        assertEquals(expectedProjects.get().getName(), actualProjects.get().getName());
        assertEquals(expectedProjects.get().getDescription(),
                actualProjects.get().getDescription());
        assertEquals(expectedProjects.get().getStartDate(), actualProjects.get().getStartDate());
        assertEquals(expectedProjects.get().getEndDate(), actualProjects.get().getEndDate());
        assertEquals(expectedProjects.get().getStatus(), actualProjects.get().getStatus());
    }

    private Optional<Project> createProject() {
        Project project = new Project();
        project.setId(2L);
        project.setName("Project2");
        project.setDescription("Project2 description");
        project.setStartDate(LocalDate.parse("2025-01-02"));
        project.setEndDate(LocalDate.parse("2025-01-08"));
        project.setStatus(Project.Status.IN_PROGRESS);
        return Optional.of(project);
    }

    private List<Project> createThreeProjects() {
        Project project1 = new Project();
        project1.setId(1L);
        project1.setName("Project1");
        project1.setDescription("Project1 description");
        project1.setStartDate(LocalDate.parse("2025-01-01"));
        project1.setEndDate(LocalDate.parse("2025-01-07"));
        project1.setStatus(Project.Status.INITIATED);
        Project project2 = new Project();
        project2.setId(2L);
        project2.setName("Project2");
        project2.setDescription("Project2 description");
        project2.setStartDate(LocalDate.parse("2025-01-02"));
        project2.setEndDate(LocalDate.parse("2025-01-08"));
        project2.setStatus(Project.Status.IN_PROGRESS);
        Project project3 = new Project();
        project3.setId(3L);
        project3.setName("Project3");
        project3.setDescription("Project3 description");
        project3.setStartDate(LocalDate.parse("2025-01-03"));
        project3.setEndDate(LocalDate.parse("2025-01-09"));
        project3.setStatus(Project.Status.COMPLETED);
        return List.of(project1, project2, project3);
    }
}
