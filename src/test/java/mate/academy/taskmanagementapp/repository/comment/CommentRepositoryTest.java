package mate.academy.taskmanagementapp.repository.comment;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import mate.academy.taskmanagementapp.model.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("Return empty list when there is no comment "
            + "of user with given id and task with given id")
    @Sql(scripts = "classpath:/database/project/add-three-projects.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/database/task/add-three-tasks.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/database/comment/add-three-comments.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/database/comment/remove-all-comments.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = "classpath:/database/task/remove-all-tasks.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = "classpath:/database/project/remove-all-projects.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllCommentsByUserIdAndTaskId_IncorrectId_ReturnsEmptyList() {
        List<Comment> actualComments = commentRepository.findAllByUserIdAndTaskId(1L, 4L,
                PageRequest.of(0, 10));
        assertEquals(0, actualComments.size());
    }

    @Test
    @DisplayName("Find all comments with given user id and task id")
    @Sql(scripts = "classpath:/database/project/add-three-projects.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/database/task/add-three-tasks.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/database/comment/add-three-comments.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/database/comment/remove-all-comments.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = "classpath:/database/task/remove-all-tasks.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = "classpath:/database/project/remove-all-projects.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAllCommentsByUserIdAndTaskId_CorrectID_ReturnsAllCorrectComments() {
        List<Comment> expectedComments = createTwoComments();
        List<Comment> actualComments = commentRepository.findAllByUserIdAndTaskId(2L, 1L,
                PageRequest.of(0, 10));
        assertNotNull(actualComments);
        assertEquals(expectedComments.size(), actualComments.size());
        assertEquals(expectedComments.get(0).getId(), actualComments.get(0).getId());
        assertEquals(expectedComments.get(1).getId(), actualComments.get(1).getId());
        assertEquals(expectedComments.get(0).getText(), actualComments.get(0).getText());
        assertEquals(expectedComments.get(1).getText(), actualComments.get(1).getText());
        assertEquals(expectedComments.get(0).getTimestamp(), actualComments.get(0).getTimestamp());
        assertEquals(expectedComments.get(1).getTimestamp(), actualComments.get(1).getTimestamp());
    }

    private List<Comment> createTwoComments() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Comment comment1 = new Comment();
        comment1.setId(1L);
        comment1.setText("Comment 1 text");
        comment1.setTimestamp(LocalDateTime.parse("2025-01-01 14:19:11", formatter));
        Comment comment2 = new Comment();
        comment2.setId(2L);
        comment2.setText("Comment 2 text");
        comment2.setTimestamp(LocalDateTime.parse("2025-01-02 17:21:17", formatter));
        return List.of(comment1, comment2);
    }
}
