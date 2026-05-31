package com.whisperspace.Whisperspace_backend.room.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateRoom {
    private String CustomName;
    private String RoomName;
}
