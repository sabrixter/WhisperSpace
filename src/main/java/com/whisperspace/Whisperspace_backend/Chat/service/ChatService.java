package com.whisperspace.Whisperspace_backend.Chat.service;

import com.whisperspace.Whisperspace_backend.Chat.dto.Chatdto;
import com.whisperspace.Whisperspace_backend.Chat.entity.Chat;
import com.whisperspace.Whisperspace_backend.Chat.repository.ChatRepository;
import com.whisperspace.Whisperspace_backend.auth.entity.user;
import com.whisperspace.Whisperspace_backend.auth.repository.AuthRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@AllArgsConstructor
public class ChatService {
    private final ChatRepository chatrepo;
    private final AuthRepository authrepo;

    public List<Chatdto> getconvochats(@RequestParam Long convoid) {
//        List<Chat> chats = chatrepo.findAllByConversationIdOrderByTimestampAsc(convoid);
        return chatrepo.findAllByConversationIdOrderByTimestampAsc(convoid)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private Chatdto toDto(Chat chat) {
        user User = authrepo.findByUsernameIgnoreCase(chat.getUsername());
        return new Chatdto(
                chat.getMessage(),
                User.getUsername(),
                chat.getTimestamp()
        );
    }
}
