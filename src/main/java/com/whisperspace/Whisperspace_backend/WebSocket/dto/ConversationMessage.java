package com.whisperspace.Whisperspace_backend.WebSocket.dto;

import com.whisperspace.Whisperspace_backend.auth.entity.user;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversationMessage {
    private Long conversationId;
    private String sender;
    private String message;
}
