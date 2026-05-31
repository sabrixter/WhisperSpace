package com.whisperspace.Whisperspace_backend.room.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomPresenceEvent {
    private String event;
    private String roomCode;
    private String customName;
}
