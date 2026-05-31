package com.whisperspace.Whisperspace_backend.room.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JoinRoom {
    private String roomCode;
    private String CustomName;
}
