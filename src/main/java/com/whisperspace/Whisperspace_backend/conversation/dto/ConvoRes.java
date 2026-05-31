package com.whisperspace.Whisperspace_backend.conversation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ConvoRes {
    private Long convoid;
    private String name;
    private String createdBy;
    private LocalDateTime createdAt;
    private List<String> participants;
}
