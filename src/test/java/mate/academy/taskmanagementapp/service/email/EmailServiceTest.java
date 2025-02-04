package mate.academy.taskmanagementapp.service.email;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.mail.internet.MimeMessage;
import mate.academy.taskmanagementapp.service.email.impl.EmailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {
    @Mock
    private JavaMailSender mailSender;
    @Mock
    private MimeMessage mimeMessage;
    @InjectMocks
    private EmailServiceImpl emailService;

    @BeforeEach
    void setUp() {
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
    }

    @Test
    @DisplayName("Send email")
    void sendEmail_CorrectRequests_ThrowsNoException() {
        String to = "test@example.com";
        String subject = "Test Email";
        String text = "This is a test email";
        emailService.sendEmail(to, subject, text);
        verify(mailSender, times(1)).send(mimeMessage);
    }

    @Test
    @DisplayName("Fail in sending email")
    void sendEmail_IncorrectRequests_ThrowsException() {
        String incorrectTo = "";
        String subject = "Test Email";
        String text = "This is a test email";
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                emailService.sendEmail(incorrectTo, subject, text));
        assert exception.getMessage().contains("Error sending email");
    }
}
