package com.my_project.task_management.api.controller;

import com.my_project.task_management.model.Notification;
import com.my_project.task_management.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    private NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestParam String toEmail, @RequestParam String subject, @RequestParam String body) {
        notificationService.sendNotification(toEmail, subject, body);
        return ResponseEntity.status(HttpStatus.OK).body("Notification email sent successfully.");
    }

}
