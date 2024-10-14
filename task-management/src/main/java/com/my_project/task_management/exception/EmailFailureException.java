package com.my_project.task_management.exception;

public class EmailFailureException extends RuntimeException {
    public EmailFailureException(String message) {
        super(message);
    }
}
