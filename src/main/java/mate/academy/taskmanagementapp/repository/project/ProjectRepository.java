package mate.academy.taskmanagementapp.repository.project;

import java.util.Optional;
import java.util.Set;
import mate.academy.taskmanagementapp.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("FROM Project p JOIN FETCH User u WHERE u.id = :userId AND p.id = :projectId")
    Optional<Project> findByUserIdAndProjectId(Long userId, Long projectId);

    @Query("FROM Project p JOIN FETCH User u WHERE u.id = :userId")
    Set<Project> findAllByUserId(Long userId);
}
