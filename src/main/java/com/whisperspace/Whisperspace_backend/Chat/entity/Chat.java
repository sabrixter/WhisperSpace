package com.whisperspace.Whisperspace_backend.Chat.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Document(collection = "messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class Chat {
    @Id
    private String id;

    private Long conversationId;

    private String username;

    private String message;

    private LocalDateTime timestamp;
}
