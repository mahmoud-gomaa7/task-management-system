package com.my_project.task_management.service;

import com.my_project.task_management.exception.EmailFailureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Value("${email.from}")
    private String emailFrom;

    private JavaMailSender javaMailSender;


    public NotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendNotification(String toEmail, String subject, String body) {
        try {
            // Create a simple mail message
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailFrom);
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);

            // Send the email
            javaMailSender.send(message);
        } catch (Exception ex) {
            throw new EmailFailureException("Failed to send notification email: " + ex.getMessage());
        }

    }
}
