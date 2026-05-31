package com.whisperspace.Whisperspace_backend.WebSocket.controller;

import com.whisperspace.Whisperspace_backend.Chat.entity.Chat;
import com.whisperspace.Whisperspace_backend.Chat.repository.ChatRepository;
import com.whisperspace.Whisperspace_backend.WebSocket.dto.ConversationMessage;
import com.whisperspace.Whisperspace_backend.WebSocket.dto.RoomMessage;
import com.whisperspace.Whisperspace_backend.auth.repository.AuthRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class WebSocketController {

    private final ChatRepository chatrepo;
    private final AuthRepository authrepo;
    private final SimpMessagingTemplate messagingTemplate;
    public WebSocketController(ChatRepository chatrepo, AuthRepository authrepo,SimpMessagingTemplate messagingTemplate) {
        this.chatrepo = chatrepo;
        this.authrepo = authrepo;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.conversation.send")
    public void sendConvoMessage(@Payload ConversationMessage message){
        Chat chat = Chat.builder()
                .conversationId(message.getConversationId())
                .username(message.getSender())
                .message(message.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        chatrepo.save(chat);
        messagingTemplate.convertAndSend("/topic/conversations/" + chat.getConversationId(), chat);
    }

    @MessageMapping("/chat.room.send")
    public void sendRoomMessage(@Payload RoomMessage message){
        LocalDateTime timestamp = LocalDateTime.now();
        message.setTimestamp(timestamp);
        messagingTemplate.convertAndSend("/topic/rooms/" + message.getRoomcode(), message);
    }
}
