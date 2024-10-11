package com.my_project.chatbot.service;

import com.google.cloud.dialogflow.v2.*;
import com.my_project.chatbot.model.ChatResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DialogflowService {
    private static final String PROJECT_ID = "simplechatbot-438219";
    private static final String SESSION_ID = UUID.randomUUID().toString();
    private static final String LANGUAGE_CODE = "en-US";

    public ChatResponse getDialogflowResponse(String userMessage) {

        try (SessionsClient sessionsClient = SessionsClient.create()) {
            SessionName session = SessionName.of(PROJECT_ID, SESSION_ID);

            TextInput textInput = TextInput.newBuilder().setText(userMessage).setLanguageCode(LANGUAGE_CODE).build();
            QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

            DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);
            String botResponse = response.getQueryResult().getFulfillmentText();

            return new ChatResponse(botResponse);
        } catch (Exception ex) {

            ex.printStackTrace();
            return new ChatResponse("Error processing your request.");

        }
    }

}
