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

    // Handle POST request at /api/whatsapp/message
    @PostMapping("/message")
    public MessageResponse receiveMessage(@RequestBody MessageRequest request) {
        return service.processMessage(request);
    }
}