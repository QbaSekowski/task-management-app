package mate.academy.taskmanagementapp.repository.comment;

import mate.academy.taskmanagementapp.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
