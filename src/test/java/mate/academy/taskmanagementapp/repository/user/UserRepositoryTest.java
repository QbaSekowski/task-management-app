package mate.academy.taskmanagementapp.repository.user;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import mate.academy.taskmanagementapp.exception.EntityNotFoundException;
import mate.academy.taskmanagementapp.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Find user by given correct id")
    @Sql(scripts = "classpath:/database/user/add-test-user.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/database/user/remove-test-user.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findUserById_Ok() {
        User expectedUser = createUser();
        User actualUser = (User) userRepository.findById(1L).get();
        assertNotNull(actualUser);
        assertEquals(expectedUser.getId(), actualUser.getId());
        assertEquals(expectedUser.getUsername(), actualUser.getUsername());
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        assertEquals(expectedUser.getPassword(), actualUser.getPassword());
        assertEquals(expectedUser.getFirstName(), actualUser.getFirstName());
        assertEquals(expectedUser.getLastName(), actualUser.getLastName());
    }

    @Test
    @DisplayName("Find user by given correct username")
    @Sql(scripts = "classpath:/database/user/add-test-user.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/database/user/remove-test-user.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findUserByUsername_Ok() {
        User expectedUser = createUser();
        User actualUser = (User) userRepository.findByUsername("admin").get();
        assertNotNull(actualUser);
        assertEquals(expectedUser.getId(), actualUser.getId());
        assertEquals(expectedUser.getUsername(), actualUser.getUsername());
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        assertEquals(expectedUser.getPassword(), actualUser.getPassword());
        assertEquals(expectedUser.getFirstName(), actualUser.getFirstName());
        assertEquals(expectedUser.getLastName(), actualUser.getLastName());
    }

    @Test
    @DisplayName("Find user by given correct email")
    @Sql(scripts = "classpath:/database/user/add-test-user.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/database/user/remove-test-user.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findUserByCorrectEmail_Ok() {
        User expectedUser = createUser();
        User actualUser = (User) userRepository.findUserByEmail("admin@gmail.com").get();
        assertNotNull(actualUser);
        assertEquals(expectedUser.getId(), actualUser.getId());
        assertEquals(expectedUser.getUsername(), actualUser.getUsername());
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        assertEquals(expectedUser.getPassword(), actualUser.getPassword());
        assertEquals(expectedUser.getFirstName(), actualUser.getFirstName());
        assertEquals(expectedUser.getLastName(), actualUser.getLastName());
    }

    @Test
    @DisplayName("Throw EntityNotFoundException when user not found by ID")
    @Sql(scripts = "classpath:/database/user/add-test-user.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/database/user/remove-test-user.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findUserByIncorrectId_throwEntityNotFoundException() {
        Long id = 8L;
        String expectedMessage = "User with id " + id + " not found";
        EntityNotFoundException actualException = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> userRepository.findById(id).orElseThrow(() ->
                        new EntityNotFoundException(expectedMessage)));
        assertEquals(expectedMessage, actualException.getMessage());
    }

    private User createUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("admin");
        user.setPassword("12345678");
        user.setEmail("admin@gmail.com");
        user.setFirstName("Admin");
        user.setLastName("Administrator");
        return user;
    }
}
