package com.whisperspace.Whisperspace_backend.room.controller;

import com.whisperspace.Whisperspace_backend.room.dto.CreateRoom;
import com.whisperspace.Whisperspace_backend.room.dto.CreateRoomRes;
import com.whisperspace.Whisperspace_backend.room.dto.JoinRoom;
import com.whisperspace.Whisperspace_backend.room.service.RoomService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/room")
public class RoomController {

    private RoomService roomService;
    @PostMapping("/create")
    public CreateRoomRes createroom(@RequestBody CreateRoom req){
        return roomService.create(req);
    }

    @PostMapping("/join")
    public String joinroom(@RequestBody JoinRoom req){
        return roomService.join(req);
    }

    @DeleteMapping
    public void deleteRoom(@RequestParam String roomcode){
        roomService.endsession(roomcode);
    }

    @PostMapping("/leave")
    public void leaveroom(@RequestParam String roomcode, @RequestParam String name){
        roomService.leave(roomcode, name);
    }
}
