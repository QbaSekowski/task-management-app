package mate.academy.taskmanagementapp.repository.project;

import java.util.List;
import java.util.Optional;
import mate.academy.taskmanagementapp.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT p FROM Project p "
            + "JOIN FETCH p.users u "
            + "WHERE u.id = :userId AND p.id = :projectId")
    Optional<Project> findByUserIdAndProjectId(Long userId, Long projectId);

    @Query("SELECT p FROM Project p "
            + "JOIN FETCH p.users u "
            + "WHERE u.id = :userId")
    List<Project> findAllByUserId(Long userId);
}
