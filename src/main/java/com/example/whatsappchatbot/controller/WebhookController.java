package com.example.whatsappchatbot.controller;

import com.example.whatsappchatbot.util.WhatsAppApiUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    private static final String VERIFY_TOKEN = "mywhatsapptoken";

    // ✅ Verification: Required by Meta
    @GetMapping
    public String verifyWebhook(
            @RequestParam("hub.mode") String mode,
            @RequestParam("hub.challenge") String challenge,
            @RequestParam("hub.verify_token") String token) {

        if ("subscribe".equals(mode) && VERIFY_TOKEN.equals(token)) {
            return challenge;
        } else {
            return "Verification failed";
        }
    }

    // ✅ Receive and handle messages
    @PostMapping
    public void receiveMessage(@RequestBody String payload) {
        System.out.println("📩 Webhook called:");
        System.out.println(payload);

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(payload);

            // Navigate through the JSON to extract message
            JsonNode messages = root
                .path("entry").get(0)
                .path("changes").get(0)
                .path("value")
                .path("messages");

            if (messages.isArray() && messages.size() > 0) {
                String from = messages.get(0).path("from").asText(); // sender number
                String text = messages.get(0).path("text").path("body").asText(); // user message

                System.out.println("📨 From: " + from + " | Message: " + text);

                // Simple auto-reply logic
                String reply;
                switch (text.toLowerCase()) {
                    case "hi", "hello" -> reply = "Hi there! 👋 How can I assist you today?";
                    case "bye" -> reply = "Goodbye! 👋 See you soon.";
                    default -> reply = "I'm a bot 🤖. You said: " + text;
                }

                // ✅ Send reply using WhatsApp API
                WhatsAppApiUtil.sendMessage(from, reply);
            }

        } catch (Exception e) {
            System.err.println("❌ Error parsing webhook payload: " + e.getMessage());
        }
    }
}
