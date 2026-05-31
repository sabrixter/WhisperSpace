package com.whisperspace.Whisperspace_backend.Chat.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Chatdto {
    private String message;
    private String sender;
    private LocalDateTime timestamp;

    public Chatdto(String message, String sender, LocalDateTime timestamp) {
        this.message = message;
        this.sender = sender;
        this.timestamp = timestamp;
    }
}
