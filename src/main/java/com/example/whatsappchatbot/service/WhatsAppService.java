package com.example.whatsappchatbot.service;

import com.example.whatsappchatbot.dto.MessageRequest;
import com.example.whatsappchatbot.dto.MessageResponse;
import com.example.whatsappchatbot.model.ChatMessage;
import com.example.whatsappchatbot.util.WhatsAppApiUtil;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class WhatsAppService {

    public MessageResponse processMessage(MessageRequest request) {
        String userMessage = request.getMessage();
        String reply;

        // Simple chatbot logic
        switch (userMessage.toLowerCase()) {
            case "hi", "hello" -> reply = "Hello! How can I help you today?";
            case "bye" -> reply = "Goodbye! Have a great day!";
            default -> reply = "Sorry, I didn't understand that. Can you try again?";
        }

        // Send reply via WhatsApp Cloud API
        WhatsAppApiUtil.sendMessage(request.getSender(), reply);

        // Save chat message to Firebase Firestore
        saveChatMessage(request.getSender(), userMessage, reply);

        // Return response to controller
        return new MessageResponse(reply);
    }

    private void saveChatMessage(String sender, String message, String response) {
        Firestore db = FirestoreClient.getFirestore();

        ChatMessage chat = new ChatMessage();
        chat.setSender(sender);
        chat.setMessage(message);
        chat.setResponse(response);

        // Format timestamp
        chat.setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        try {
            ApiFuture<DocumentReference> future = db.collection("chats").add(chat);
            DocumentReference docRef = future.get(); // Wait for result
            System.out.println("✅ Chat saved with ID: " + docRef.getId());
        } catch (Exception e) {
            System.err.println("❌ Error saving chat to Firestore: " + e.getMessage());
        }
    }
}