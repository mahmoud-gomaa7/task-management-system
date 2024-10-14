package com.my_project.task_management.service;

import com.my_project.task_management.exception.TaskNotFoundException;
import com.my_project.task_management.model.Task;
import com.my_project.task_management.model.TaskHistory;
import com.my_project.task_management.model.User;
import com.my_project.task_management.model.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private TaskRepository taskRepository;
    private TaskHistoryService taskHistoryService;
    private NotificationService notificationService;
    private UserService userService;

    public TaskService(TaskRepository taskRepository, TaskHistoryService taskHistoryService, NotificationService notificationService, UserService userService) {
        this.taskRepository = taskRepository;
        this.taskHistoryService = taskHistoryService;
        this.notificationService = notificationService;
        this.userService = userService;
    }

    // Method to get the user tasks
    public List<Task> getAllTasksForCurrentUser() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.getUserByUsername(currentUsername);
        return taskRepository.findByUserId(currentUser.getId());
    }

    // Method to get all tasks for all users
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with ID " + id + " not found"));
    }

    @Transactional
    public Task createTask(Task task) {
        Task newTask = taskRepository.save(task);

        // Send email notification after task creation
        notificationService.sendNotification(
                newTask.getUser().getEmail(),
                "New Task Created",
                "A new task titled '" + newTask.getTitle() + "' has been created."
        );

        return newTask;
    }

    @Transactional
    public Task updateTask(Long taskId, Task updatedTask) {
        Task existingTask = getTaskById(taskId);

        // Log task history before updating
        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setTask(existingTask);
        taskHistory.setOldStatus(existingTask.getStatus());
        taskHistory.setNewStatus(updatedTask.getStatus());
        taskHistory.setUpdatedAt(LocalDateTime.now());
        taskHistoryService.logTaskHistory(taskHistory);

        // Update the task fields
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setStatus(updatedTask.getStatus());
        existingTask.setPriority(updatedTask.getPriority());
        existingTask.setDueDate(updatedTask.getDueDate());

        try {
            Task savedTask = taskRepository.save(existingTask);
            // Send email notification after updating the task
            notificationService.sendNotification(
                    savedTask.getUser().getEmail(),
                    "Task Updated",
                    "The task titled '" + savedTask.getTitle() + "' has been updated."
            );
            return savedTask;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to update task due to an unexpected error: " + ex.getMessage());
        }

    }

    @Transactional
    public void deleteTask(Long id) {
        Task task = getTaskById(id);
        try {
            taskRepository.delete(task);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to delete task due to an unexpected error: " + ex.getMessage());
        }
    }

    public List<Task> searchTasksByTitleAndStatus(String title, Task.Status status) {
        return taskRepository.findByTitleContainingAndStatus(title, status);
    }

    public List<Task> searchTasksByDueDate(LocalDate startDate, LocalDate endDate) {
        return taskRepository.findByDueDateBetween(startDate, endDate);
    }

}
