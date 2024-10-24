package mate.academy.taskmanagementapp.repository.attachment;

import mate.academy.taskmanagementapp.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
