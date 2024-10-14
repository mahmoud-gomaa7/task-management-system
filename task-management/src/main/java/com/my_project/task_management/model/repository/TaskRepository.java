package com.my_project.task_management.model.repository;

import com.my_project.task_management.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByTitle(String title);

    List<Task> findByStatus(Task.Status status);

    List<Task> findByTitleContaining(String title);

    List<Task> findByDueDateBetween(LocalDate startDate, LocalDate endDate);

    // Custom Query Example for filtering by multiple fields
    @Query("SELECT t FROM Task t WHERE t.title LIKE %?1% AND t.status = ?2")
    List<Task> findByTitleContainingAndStatus(String title, Task.Status status);

    @Query("SELECT t FROM Task t WHERE t.user.id = :userId")
    List<Task> findByUserId(@Param("userId") Long userId);
}
