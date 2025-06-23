package com.example.whatsappchatbot.controller;

import com.example.whatsappchatbot.dto.MessageRequest;
import com.example.whatsappchatbot.dto.MessageResponse;
import com.example.whatsappchatbot.service.WhatsAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/whatsapp")
public class WhatsAppController {

    @Autowired
    private WhatsAppService service;

    // ✅ Root test endpoint for Render
    @GetMapping("/")
    public String home() {
        return "✅ WhatsApp Chatbot is live on Render!";
    }

    // ✅ POST endpoint to receive messages
    @PostMapping("/message")
    public MessageResponse receiveMessage(@RequestBody MessageRequest request) {
        return service.processMessage(request);
    }
}
