package com.my_project.task_management.model.repository;

import com.my_project.task_management.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
