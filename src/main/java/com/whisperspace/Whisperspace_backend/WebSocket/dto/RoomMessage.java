package com.whisperspace.Whisperspace_backend.WebSocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomMessage {
    private String roomcode;
    private String SenderCustomname;
    private String message;
    private LocalDateTime timestamp;
}
