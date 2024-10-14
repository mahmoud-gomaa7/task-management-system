package com.my_project.task_management.service;

import com.my_project.task_management.exception.TaskHistoryException;
import com.my_project.task_management.model.Task;
import com.my_project.task_management.model.TaskHistory;
import com.my_project.task_management.model.repository.TaskHistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskHistoryService {

    private TaskHistoryRepository taskHistoryRepository;

    public TaskHistoryService(TaskHistoryRepository taskHistoryRepository) {
        this.taskHistoryRepository = taskHistoryRepository;
    }

    // Method to save a task history record

    public TaskHistory logTaskHistory(TaskHistory taskHistory) {
        try {
            return taskHistoryRepository.save(taskHistory);
        } catch (Exception ex) {
            throw new TaskHistoryException("Failed to log task history: " + ex.getMessage());
        }
    }

    // Method to retrieve task history by task ID
    public List<TaskHistory> getTaskHistoryByTaskId(Long taskId) {
        try {
            return taskHistoryRepository.findByTaskId(taskId);
        } catch (Exception ex) {
            throw new TaskHistoryException("Failed to retrieve task history for task ID " + taskId + ": " + ex.getMessage());
        }
    }


}
