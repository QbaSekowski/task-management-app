package mate.academy.taskmanagementapp.repository.task;

import java.util.List;
import java.util.Optional;
import mate.academy.taskmanagementapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByAssigneeId(Long userId);

    Optional<Task> findByAssigneeIdAndId(Long assigneeId, Long taskId);
}
