package mate.academy.taskmanagementapp.exception;

import java.time.LocalDateTime;

public record ExceptionResponse(String message, LocalDateTime localDateTime) {
}
