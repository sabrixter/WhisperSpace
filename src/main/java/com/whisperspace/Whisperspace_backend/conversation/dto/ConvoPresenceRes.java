package com.whisperspace.Whisperspace_backend.conversation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConvoPresenceRes {
    private String username;
    private String event;
    private Long convoid;
}
