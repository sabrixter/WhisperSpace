package com.whisperspace.Whisperspace_backend.conversation.repository;

import com.whisperspace.Whisperspace_backend.conversation.entity.Conversations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConvoRepo extends JpaRepository<Conversations, Long> {
    @Query("""
        SELECT DISTINCT c
        FROM Conversations c
        LEFT JOIN c.participants p
        WHERE c.createdByUserId = :userId
           OR (p.user.id = :userId AND p.status = 'ACCEPTED')
    """)
    List<Conversations> findAllUserConversations(@Param("userId") Long userId);
}
