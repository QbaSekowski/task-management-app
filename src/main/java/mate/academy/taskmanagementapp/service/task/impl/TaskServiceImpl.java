package mate.academy.taskmanagementapp.service.task.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.taskmanagementapp.dto.task.CreateTaskRequestDto;
import mate.academy.taskmanagementapp.dto.task.TaskDto;
import mate.academy.taskmanagementapp.exception.EntityNotFoundException;
import mate.academy.taskmanagementapp.mapper.TaskMapper;
import mate.academy.taskmanagementapp.model.Project;
import mate.academy.taskmanagementapp.model.Task;
import mate.academy.taskmanagementapp.model.User;
import mate.academy.taskmanagementapp.repository.project.ProjectRepository;
import mate.academy.taskmanagementapp.repository.task.TaskRepository;
import mate.academy.taskmanagementapp.repository.user.UserRepository;
import mate.academy.taskmanagementapp.service.task.TaskService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public TaskDto createTask(Long userId, CreateTaskRequestDto createTaskRequestDto) {
        Project project = projectRepository.findByUserIdAndProjectId(userId,
                createTaskRequestDto.projectId()).orElseThrow(
                        () -> new EntityNotFoundException("Project by id "
                        + createTaskRequestDto.projectId() + " and user id "
                                + userId + " not found"));
        User user = userRepository.findById(createTaskRequestDto.assigneeId()).orElseThrow(
                () -> new EntityNotFoundException("User by id "
                        + createTaskRequestDto.assigneeId() + " not found"));
        Task task = taskMapper.toModel(createTaskRequestDto);
        task.setProject(project);
        task.setAssignee(user);
        return taskMapper.toDto(taskRepository.save(task));
    }

    @Override
    public List<TaskDto> getAllTasks(Long userId, Pageable pageable) {
        return taskRepository.findAllByAssigneeId(userId, pageable).stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto getTaskById(Long userId, Long taskId) {
        return taskMapper.toDto(taskRepository.findByAssigneeIdAndId(userId, taskId).orElseThrow(
                () -> new EntityNotFoundException("Task by id "
                        + taskId + " and user by id " + userId + " not found")));
    }

    @Transactional
    @Override
    public TaskDto updateTask(Long userId, Long taskId, CreateTaskRequestDto createTaskRequestDto) {
        Task task = taskRepository.findByAssigneeIdAndId(userId, taskId).orElseThrow(
                () -> new EntityNotFoundException("Task by id "
                        + taskId + " and user by id " + userId + " not found"));
        Task updatedTask = taskMapper.updateTask(createTaskRequestDto, task);
        return taskMapper.toDto(taskRepository.save(updatedTask));
    }

    @Transactional
    @Override
    public void deleteTask(Long userId, Long taskId) {
        Task task = taskRepository.findByAssigneeIdAndId(userId, taskId).orElseThrow(
                () -> new EntityNotFoundException("Task by id "
                        + taskId + " and user by id " + userId + " not found"));
        taskRepository.deleteById(task.getId());
    }
}
