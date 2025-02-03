package mate.academy.taskmanagementapp;

import mate.academy.taskmanagementapp.config.TestEnvConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestEnvConfig.class)
class TaskManagementAppApplicationTests {

    @Test
    void contextLoads() {
    }

}
