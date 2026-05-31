package com.whisperspace.Whisperspace_backend.room.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateRoomRes
{
    private String roomCode;
    private String roomName;
    public  CreateRoomRes(String roomCode,String roomName)
    {
        this.roomCode=roomCode;
        this.roomName=roomName;
    }
}
