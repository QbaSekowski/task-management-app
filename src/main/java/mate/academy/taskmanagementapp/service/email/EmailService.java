package mate.academy.taskmanagementapp.service.email;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
