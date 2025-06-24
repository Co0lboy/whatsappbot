package com.example.whatsappchatbot.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    // ✅ Verification from Meta (GET request)
    @GetMapping
    public String verifyWebhook(
            @RequestParam("hub.mode") String mode,
            @RequestParam("hub.challenge") String challenge,
            @RequestParam("hub.verify_token") String verifyToken) {

        if ("subscribe".equals(mode) && "YOUR_VERIFY_TOKEN".equals(verifyToken)) {
            return challenge;
        } else {
            return "Verification failed";
        }
    }

    // ✅ Receiving WhatsApp messages (POST request)
    @PostMapping
    public void receiveMessage(@RequestBody String payload) {
        System.out.println("📩 Incoming WhatsApp message:");
        System.out.println(payload);
        // Later: parse and handle the payload
    }
}
