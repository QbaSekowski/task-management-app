package mate.academy.taskmanagementapp.repository.role;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import mate.academy.taskmanagementapp.model.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoleRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    @DisplayName("Find role by given correct name")
    @Sql(scripts = "classpath:/database/role/add-test-role.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/database/role/remove-test-role.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findRoleByName_Ok() {
        Role expectedRole = new Role();
        expectedRole.setId(1L);
        expectedRole.setName(Role.RoleName.USER);
        Role actualRole = roleRepository.findRoleByName(Role.RoleName.USER).get();
        assertNotNull(actualRole);
        assertEquals(expectedRole.getName(), actualRole.getName());
    }
}
