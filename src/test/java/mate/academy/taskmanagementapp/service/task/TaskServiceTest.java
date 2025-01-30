package mate.academy.taskmanagementapp.service.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import mate.academy.taskmanagementapp.dto.task.CreateTaskRequestDto;
import mate.academy.taskmanagementapp.dto.task.TaskDto;
import mate.academy.taskmanagementapp.exception.EntityNotFoundException;
import mate.academy.taskmanagementapp.mapper.TaskMapper;
import mate.academy.taskmanagementapp.model.Project;
import mate.academy.taskmanagementapp.model.Task;
import mate.academy.taskmanagementapp.model.User;
import mate.academy.taskmanagementapp.repository.project.ProjectRepository;
import mate.academy.taskmanagementapp.repository.task.TaskRepository;
import mate.academy.taskmanagementapp.repository.user.UserRepository;
import mate.academy.taskmanagementapp.service.task.impl.TaskServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskMapper taskMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProjectRepository projectRepository;
    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    @DisplayName("Create task")
    public void createTask_CorrectTaskRequest_ReturnsTaskDto() {
        Task task = createTask();
        Project project = createProject();
        User user = createUser();
        CreateTaskRequestDto createTaskRequestDto = new CreateTaskRequestDto(task.getName(),
                task.getDescription(), task.getPriority(), task.getStatus(), task.getDueDate(),
                project.getId(), user.getId(), new HashSet<>());
        TaskDto expected = new TaskDto(task.getId(), task.getName(),
                task.getDescription(), task.getPriority().name(), task.getStatus().name(),
                task.getDueDate(), project.getId(), user.getId(), new HashSet<>());
        when(taskMapper.toDto(task)).thenReturn(expected);
        when(taskMapper.toModel(createTaskRequestDto)).thenReturn(task);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(taskRepository.save(task)).thenReturn(task);
        when(projectRepository.findByUserIdAndProjectId(user.getId(), project.getId()))
                .thenReturn(Optional.of(project));
        TaskDto actual = taskService.createTask(user.getId(), createTaskRequestDto);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Retrieve task details")
    public void getTaskDetails_CorrectTaskRequest_ReturnsTaskDto() {
        Task task = createTask();
        Project project = createProject();
        User user = createUser();
        TaskDto expected = new TaskDto(task.getId(), task.getName(),
                task.getDescription(), task.getPriority().name(), task.getStatus().name(),
                task.getDueDate(), project.getId(), user.getId(), new HashSet<>());
        when(taskMapper.toDto(task)).thenReturn(expected);
        when(taskRepository.findByAssigneeIdAndId(user.getId(), task.getId()))
                .thenReturn(Optional.of(task));
        TaskDto actual = taskService.getTaskById(user.getId(), task.getId());
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Retrieve task details with incorrect id - throw EntityNotFoundException")
    public void getTaskDetails_IncorrectTaskRequest_ThrowsEntityNotFoundException() {
        Long incorrectId = 15L;
        when(taskRepository.findByAssigneeIdAndId(incorrectId, incorrectId))
                .thenReturn(Optional.empty());
        String expectedInfo = "Task by id "
                + incorrectId + " and user by id " + incorrectId + " not found";
        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class,
                        () -> taskService.getTaskById(incorrectId, incorrectId));
        String actualInfo = exception.getMessage();
        assertEquals(expectedInfo, actualInfo);
        verify(taskRepository, times(1)).findByAssigneeIdAndId(any(), any());
        verifyNoMoreInteractions(taskRepository);
    }

    private Task createTask() {
        Task task = new Task();
        task.setId(1L);
        task.setName("Task name");
        task.setDescription("Task description");
        task.setPriority(Task.Priority.LOW);
        task. setStatus(Task.Status.NOT_STARTED);
        task.setDueDate(LocalDate.parse("2025-01-29"));
        task.setProject(createProject());
        task.setAssignee(createUser());
        return task;
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
