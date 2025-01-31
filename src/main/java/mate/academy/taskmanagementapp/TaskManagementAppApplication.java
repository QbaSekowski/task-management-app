package mate.academy.taskmanagementapp;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TaskManagementAppApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().load();
        System.setProperty("MYSQLDB_USER", dotenv.get("MYSQLDB_USER"));
        System.setProperty("MYSQLDB_ROOT_PASSWORD", dotenv.get("MYSQLDB_ROOT_PASSWORD"));
        System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));
        System.setProperty("JWT_EXPIRATION", dotenv.get("JWT_EXPIRATION"));
        System.setProperty("MYSQLDB_DATABASE", dotenv.get("MYSQLDB_DATABASE"));
        System.setProperty("DROPBOX_ACCESS_TOKEN", dotenv.get("DROPBOX_ACCESS_TOKEN"));
        SpringApplication.run(TaskManagementAppApplication.class, args);
    }
}
