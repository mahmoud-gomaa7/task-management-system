package com.my_project.chatbot.controller;

import com.my_project.chatbot.model.ChatRequest;
import com.my_project.chatbot.model.ChatResponse;
import com.my_project.chatbot.service.DialogflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatbotController {

    private DialogflowService dialogflowService;

    @Autowired
    public ChatbotController(DialogflowService dialogflowService) {
        this.dialogflowService = dialogflowService;
    }

    @PostMapping("/message")
    public ChatResponse getChatResponse(@RequestBody ChatRequest chatRequest) {
        String userMessage = chatRequest.getMessage();
        return dialogflowService.getDialogflowResponse(userMessage);
    }
}
