package mate.academy.taskmanagementapp.repository;

import mate.academy.taskmanagementapp.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {
}
