package com.regalgrid.demo.model;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Document( collection = "chat")
public class Chat {
    private int chatId;

    private String senderId;

    private String receiverId;

    private String message;

    private LocalDateTime time;
}
