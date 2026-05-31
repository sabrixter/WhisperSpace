package com.whisperspace.Whisperspace_backend.Chat.repository;

import com.whisperspace.Whisperspace_backend.Chat.entity.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRepository extends MongoRepository<Chat, String> {
    List<Chat> findAllByConversationIdOrderByTimestampAsc(Long conversationId);
}
