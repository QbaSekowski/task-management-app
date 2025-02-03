package mate.academy.taskmanagementapp.repository.task;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import mate.academy.taskmanagementapp.model.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;

    @Test
    @DisplayName("Find all tasks by given assignee id")
    @Sql(scripts = "classpath:/database/project/add-three-projects.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/database/task/add-three-tasks.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/database/task/remove-all-tasks.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = "classpath:/database/project/remove-all-projects.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByAssigneeId_CorrectId_ReturnsAllCorrectTasks() {
        List<Task> expectedTasks = createTwoTasks();
        List<Task> actualTasks = taskRepository.findAllByAssigneeId(2L, PageRequest.of(0, 10));
        assertNotNull(actualTasks);
        assertEquals(expectedTasks.size(), actualTasks.size());
        assertEquals(expectedTasks.get(0).getId(), actualTasks.get(0).getId());
        assertEquals(expectedTasks.get(0).getDescription(), actualTasks.get(0).getDescription());
        assertEquals(expectedTasks.get(0).getPriority(), actualTasks.get(0).getPriority());
        assertEquals(expectedTasks.get(0).getStatus(), actualTasks.get(0).getStatus());
        assertEquals(expectedTasks.get(0).getDueDate(), actualTasks.get(0).getDueDate());
        assertEquals(expectedTasks.get(1).getId(), actualTasks.get(1).getId());
        assertEquals(expectedTasks.get(1).getDescription(), actualTasks.get(1).getDescription());
        assertEquals(expectedTasks.get(1).getPriority(), actualTasks.get(1).getPriority());
        assertEquals(expectedTasks.get(1).getStatus(), actualTasks.get(1).getStatus());
        assertEquals(expectedTasks.get(1).getDueDate(), actualTasks.get(1).getDueDate());
    }

    @Test
    @DisplayName("Find all tasks by given assignee id and task id")
    @Sql(scripts = "classpath:/database/project/add-three-projects.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/database/task/add-three-tasks.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/database/task/remove-all-tasks.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = "classpath:/database/project/remove-all-projects.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByAssigneeIdAndTaskId_CorrectIds_ReturnsCorrectTask() {
        Optional<Task> expectedTasks = createOneTasks();
        Optional<Task> actualTasks = taskRepository.findByAssigneeIdAndId(2L, 2L);
        assertNotNull(actualTasks);
        assertEquals(expectedTasks.get().getId(), actualTasks.get().getId());
        assertEquals(expectedTasks.get().getDescription(), actualTasks.get().getDescription());
        assertEquals(expectedTasks.get().getPriority(), actualTasks.get().getPriority());
        assertEquals(expectedTasks.get().getStatus(), actualTasks.get().getStatus());
        assertEquals(expectedTasks.get().getDueDate(), actualTasks.get().getDueDate());
    }

    @Test
    @DisplayName("Return empty Optional by given invalid assignee id or task id")
    @Sql(scripts = "classpath:/database/project/add-three-projects.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/database/task/add-three-tasks.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/database/task/remove-all-tasks.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = "classpath:/database/project/remove-all-projects.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllByAssigneeIdAndTaskId_IncorrectIds_ReturnsEmptyOptional() {
        Optional<Task> actualTasks = taskRepository.findByAssigneeIdAndId(1L, 3L);
        assertEquals(Optional.empty(), actualTasks);
    }

    private Optional<Task> createOneTasks() {
        Task task = new Task();
        task.setId(2L);
        task.setDescription("Task 2 description");
        task.setPriority(Task.Priority.MEDIUM);
        task.setStatus(Task.Status.IN_PROGRESS);
        task.setDueDate(LocalDate.parse("2025-01-07"));
        return Optional.of(task);
    }

    private List<Task> createTwoTasks() {
        Task task1 = new Task();
        task1.setId(2L);
        task1.setDescription("Task 2 description");
        task1.setPriority(Task.Priority.MEDIUM);
        task1.setStatus(Task.Status.IN_PROGRESS);
        task1.setDueDate(LocalDate.parse("2025-01-07"));
        Task task2 = new Task();
        task2.setId(3L);
        task2.setDescription("Task 3 description");
        task2.setPriority(Task.Priority.LOW);
        task2.setStatus(Task.Status.COMPLETED);
        task2.setDueDate(LocalDate.parse("2025-01-08"));
        return List.of(task1, task2);
    }
}
