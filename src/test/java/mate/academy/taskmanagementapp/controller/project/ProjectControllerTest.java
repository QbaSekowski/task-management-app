package mate.academy.taskmanagementapp.controller.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.sql.DataSource;
import mate.academy.taskmanagementapp.dto.project.CreateProjectRequestDto;
import mate.academy.taskmanagementapp.dto.project.ProjectDto;
import mate.academy.taskmanagementapp.dto.user.UserResponseDto;
import mate.academy.taskmanagementapp.model.Project;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectControllerTest {
    @Autowired
    protected static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(
            @Autowired WebApplicationContext applicationContext,
            @Autowired DataSource dataSource) throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("Create a new project")
    @WithUserDetails("Username1")
    @Sql(scripts = "classpath:database/project/add-three-projects.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/project/remove-all-projects.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void createNewProject_CorrectRequestDto_ReturnsNewProjectDto() throws Exception {
        CreateProjectRequestDto createProjectRequestDto
                = new CreateProjectRequestDto("Project4", "Project4 description",
                LocalDate.parse("2025-01-04"), LocalDate.parse("2025-01-10"));
        UserResponseDto adminDto = new UserResponseDto(1L, "Username1",
                "email1@gmail.com", "FirstName1", "LastName1");
        ProjectDto expected = new ProjectDto(4L, createProjectRequestDto.name(),
                createProjectRequestDto.description(), createProjectRequestDto.startDate(),
                createProjectRequestDto.endDate(), Project.Status.INITIATED, Set.of(adminDto));
        String requestJson = objectMapper.writeValueAsString(createProjectRequestDto);
        MvcResult result = mockMvc.perform(post("/api/projects")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        ProjectDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                ProjectDto.class);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Retrieve all user's projects")
    @WithUserDetails("Username2")
    @Sql(scripts = "classpath:database/project/add-three-projects.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/project/remove-all-projects.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getAllUsersProjects_CorrectId_ReturnsAllUsersProjectsDtos() throws Exception {
        UserResponseDto userResponseDto = new UserResponseDto(2L, "Username2",
                "email2@gmail.com", "FirstName2", "LastName2");
        ProjectDto expectedProject = new ProjectDto(2L, "Project2", "Project2 description",
                LocalDate.parse("2025-01-02"), LocalDate.parse("2025-01-08"),
                Project.Status.IN_PROGRESS, Set.of(userResponseDto));
        List<ProjectDto> expected = List.of(expectedProject);
        MvcResult result = mockMvc.perform(get("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<ProjectDto> actual = Arrays.stream(objectMapper.readValue(
                        result.getResponse().getContentAsString(), ProjectDto[].class))
                .toList();
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Retrieve project's details")
    @WithUserDetails("Username2")
    @Sql(scripts = "classpath:database/project/add-three-projects.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/project/remove-all-projects.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getProjectDetails_CorrectId_ReturnsProjectDto() throws Exception {
        UserResponseDto userResponseDto = new UserResponseDto(2L, "Username2",
                "email2@gmail.com", "FirstName2", "LastName2");
        ProjectDto expected = new ProjectDto(2L, "Project2", "Project2 description",
                LocalDate.parse("2025-01-02"), LocalDate.parse("2025-01-08"),
                Project.Status.IN_PROGRESS, Set.of(userResponseDto));
        MvcResult result = mockMvc.perform(get("/api/projects/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        ProjectDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                ProjectDto.class);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update project")
    @WithUserDetails("Username1")
    @Sql(scripts = "classpath:database/project/add-three-projects.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/project/remove-all-projects.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateProject_CorrectIdAndRequestDto_ReturnsUpdatedProjectDto() throws Exception {
        CreateProjectRequestDto createProjectRequestDto = new CreateProjectRequestDto("New name",
                "New description", LocalDate.parse("2025-01-26"),
                LocalDate.parse("2025-01-27"));
        UserResponseDto userResponseDto = new UserResponseDto(1L, "Username1",
                "email1@gmail.com", "FirstName1", "LastName1");
        ProjectDto expected = new ProjectDto(3L, "New name", "New description",
                LocalDate.parse("2025-01-26"), LocalDate.parse("2025-01-27"),
                Project.Status.COMPLETED, Set.of(userResponseDto));
        String requestJson = objectMapper.writeValueAsString(createProjectRequestDto);
        MvcResult result = mockMvc.perform(put("/api/projects/3")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        ProjectDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                ProjectDto.class);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Delete Project")
    @WithUserDetails("Username1")
    @Sql(scripts = "classpath:database/project/add-three-projects.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/project/remove-all-projects.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteProject_CorrectId_ReturnsNoContentStatus() throws Exception {
        MvcResult result = mockMvc.perform(delete("/api/projects/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
        int actual = result.getResponse().getStatus();
        assertEquals(204, actual);
    }
}
