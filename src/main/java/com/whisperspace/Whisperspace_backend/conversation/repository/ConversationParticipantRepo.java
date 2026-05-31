package com.whisperspace.Whisperspace_backend.conversation.repository;

import com.whisperspace.Whisperspace_backend.auth.entity.user;
import com.whisperspace.Whisperspace_backend.conversation.entity.ConversationParticipants;
import com.whisperspace.Whisperspace_backend.conversation.entity.Conversations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConversationParticipantRepo extends JpaRepository<ConversationParticipants, Long> {
    @Query("""
    SELECT p FROM ConversationParticipants p WHERE p.conversation =:convo AND p.user =:user
""")
    ConversationParticipants findConversationParticipants(@Param("convo") Conversations convo, @Param("user") user user);

    @Query("""
    SELECT p FROM ConversationParticipants p where p.status like "PENDING" AND p.user =:user
""")
    List<ConversationParticipants> findAllConversationParticipants(@Param("user") user user);
}
