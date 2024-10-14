package com.my_project.task_management.model.repository;

import com.my_project.task_management.model.TaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long> {
    List<TaskHistory> findByTaskId(Long id);
}
