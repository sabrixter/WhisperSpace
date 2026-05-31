package com.whisperspace.Whisperspace_backend.conversation.entity;

import com.whisperspace.Whisperspace_backend.auth.entity.user;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "conversation_participants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversationParticipants {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversations conversation;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private user user;

    @Column(nullable = false)
    private String status;

    private LocalDateTime joinedAt;
}
