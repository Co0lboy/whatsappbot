package com.example.whatsappchatbot.dto;

import lombok.Data;

@Data
public class MessageRequest {
    private String sender;
    private String message;
}