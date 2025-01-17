package mate.academy.taskmanagementapp.service.task;

import java.util.List;
import mate.academy.taskmanagementapp.dto.task.CreateTaskRequestDto;
import mate.academy.taskmanagementapp.dto.task.TaskDto;

public interface TaskService {
    TaskDto createTask(Long userId, CreateTaskRequestDto createTaskRequestDto);

    List<TaskDto> getAllTasks(Long userId);

    TaskDto getTaskById(Long userId, Long taskId);

    TaskDto updateTask(Long userId, Long taskId, CreateTaskRequestDto createTaskRequestDto);

    void deleteTask(Long userId, Long taskId);
}
