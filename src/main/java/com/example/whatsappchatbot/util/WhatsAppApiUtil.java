package com.example.whatsappchatbot.util;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class WhatsAppApiUtil {

    // ✅ Load from environment variable for security
    private static final String ACCESS_TOKEN = System.getenv("META_ACCESS_TOKEN");
    private static final String PHONE_NUMBER_ID = "659917977208507";

    public static void sendMessage(String to, String message) {
        if (ACCESS_TOKEN == null || ACCESS_TOKEN.isBlank()) {
            System.err.println("❌ ERROR: META_ACCESS_TOKEN environment variable is not set!");
            return;
        }

        String url = "https://graph.facebook.com/v18.0/" + PHONE_NUMBER_ID + "/messages";

        String body = """
            {
              "messaging_product": "whatsapp",
              "to": "%s",
              "type": "text",
              "text": {
                "body": "%s"
              }
            }
            """.formatted(to, message);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(ACCESS_TOKEN); // ✅ Secure usage
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            System.out.println("✅ WhatsApp API response: " + response.getBody());
        } catch (Exception e) {
            System.err.println("❌ Failed to send message: " + e.getMessage());
        }
    }
}
