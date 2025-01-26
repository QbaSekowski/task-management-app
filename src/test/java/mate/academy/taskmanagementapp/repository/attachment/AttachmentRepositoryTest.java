package mate.academy.taskmanagementapp.repository.attachment;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import mate.academy.taskmanagementapp.model.Attachment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AttachmentRepositoryTest {
    @Autowired
    private AttachmentRepository attachmentRepository;

    @Test
    @DisplayName("Find all attachments with given task id")
    @Sql(scripts = "classpath:/database/project/add-three-projects.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/database/task/add-three-tasks.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/database/attachment/add-three-attachments.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/database/attachment/remove-all-attachments.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = "classpath:/database/task/remove-all-tasks.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = "classpath:/database/project/remove-all-projects.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllAttachmentsByTaskId_CorrectId_ReturnsAllAttachments() {
        List<Attachment> expectedAttachments = createTwoAttachments();
        List<Attachment> actualAttachments = attachmentRepository.findAllByTaskId(2L);
        assertNotNull(actualAttachments);
        assertEquals(expectedAttachments.size(), actualAttachments.size());
        assertEquals(expectedAttachments.get(0).getId(), actualAttachments.get(0).getId());
        assertEquals(expectedAttachments.get(0).getDropboxId(), actualAttachments.get(0).getDropboxId());
        assertEquals(expectedAttachments.get(0).getFileName(), actualAttachments.get(0).getFileName());
        assertEquals(expectedAttachments.get(0).getUploadDate(), actualAttachments.get(0).getUploadDate());
        assertEquals(expectedAttachments.get(1).getId(), actualAttachments.get(1).getId());
        assertEquals(expectedAttachments.get(1).getDropboxId(), actualAttachments.get(1).getDropboxId());
        assertEquals(expectedAttachments.get(1).getFileName(), actualAttachments.get(1).getFileName());
        assertEquals(expectedAttachments.get(1).getUploadDate(), actualAttachments.get(1).getUploadDate());
    }

    private List<Attachment> createTwoAttachments() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Attachment attachment1 = new Attachment();
        attachment1.setId(2L);
        attachment1.setDropboxId("dropbox_id_2");
        attachment1.setFileName("file_name_2.txt");
        attachment1.setUploadDate(LocalDateTime.parse("2025-01-10 10:45:24", formatter));
        Attachment attachment2 = new Attachment();
        attachment2.setId(3L);
        attachment2.setDropboxId("dropbox_id_3");
        attachment2.setFileName("file_name_3.txt");
        attachment2.setUploadDate(LocalDateTime.parse("2025-01-11 11:54:21", formatter));
        return List.of(attachment1, attachment2);
    }
}
