package com.example.whatsappchatbot.util;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class WhatsAppApiUtil {

    // TODO: Replace with your actual values
    private static final String ACCESS_TOKEN = "EAANPAI5HsQsBO0SeMNS0CawebZAmehXE9yGawmr5DdMETwacOZA4fsl4N3ZCkMiZBnVZAj1XIjNyytNhQudkE4alLCuuV8orwX00TJrHiyZCmban3mcnGU7vHZAPsb2NfIdyPKJPVMCCkyrZCCztY6VfN5QTZBMKuQNB2NiZCpZBcUxjoF84VGV5AE705iXqDs3GCFwEC741vYFtkvpWi6Ekadi6xxkK8Mw1Ia6nIE0IO1MLjMHhiEZD";
    private static final String PHONE_NUMBER_ID = "659917977208507";

    public static void sendMessage(String to, String message) {
        String url = "https://graph.facebook.com/v18.0/" + PHONE_NUMBER_ID + "/messages";

        // JSON body to send
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
        headers.setBearerAuth(ACCESS_TOKEN);
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