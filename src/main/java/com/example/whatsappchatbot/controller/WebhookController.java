package com.example.whatsappchatbot.controller;

import com.example.whatsappchatbot.util.WhatsAppApiUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    private static final String VERIFY_TOKEN = "mywhatsapptoken"; // must match Meta Dashboard

    // ✅ Webhook Verification for Meta
    @GetMapping
    public String verifyWebhook(
            @RequestParam(name = "hub.mode") String mode,
            @RequestParam(name = "hub.challenge") String challenge,
            @RequestParam(name = "hub.verify_token") String token) {

        if ("subscribe".equals(mode) && VERIFY_TOKEN.equals(token)) {
            return challenge; // ✅ Echo the challenge to verify the webhook
        } else {
            return "Verification failed";
        }
    }

    // ✅ Receive WhatsApp messages
    @PostMapping
    public void receiveMessage(@RequestBody String payload) {
        System.out.println("📩 Webhook called:");
        System.out.println(payload);

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(payload);

            // Extract message array from JSON
            JsonNode messages = root
                    .path("entry").get(0)
                    .path("changes").get(0)
                    .path("value")
                    .path("messages");

            if (messages != null && messages.isArray() && messages.size() > 0) {
                String from = messages.get(0).path("from").asText(); // sender number
                String text = messages.get(0).path("text").path("body").asText(); // user message

                System.out.println("📨 From: " + from + " | Message: " + text);

                // Auto-reply logic
                String reply;
                switch (text.toLowerCase()) {
                    case "hi", "hello" -> reply = "Hi there! 👋 How can I assist you today?";
                    case "bye" -> reply = "Goodbye! 👋 See you soon.";
                    default -> reply = "I'm a bot 🤖. You said: " + text;
                }

                // ✅ Send reply back via WhatsApp API
                WhatsAppApiUtil.sendMessage(from, reply);
            }

        } catch (Exception e) {
            System.err.println("❌ Error parsing webhook payload: " + e.getMessage());
        }
    }
}
