package com.whisperspace.Whisperspace_backend.room.service;

import com.whisperspace.Whisperspace_backend.room.dto.CreateRoom;
import com.whisperspace.Whisperspace_backend.room.dto.CreateRoomRes;
import com.whisperspace.Whisperspace_backend.room.dto.JoinRoom;
import com.whisperspace.Whisperspace_backend.room.dto.RoomPresenceEvent;
import com.whisperspace.Whisperspace_backend.room.entity.room;
import com.whisperspace.Whisperspace_backend.room.repository.RoomRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.SecureRandom;

@Service
public class RoomService {

    private static final String ALPHANUMERIC = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final SecureRandom RANDOM = new SecureRandom();
    private final RoomRepository roomrepo;
    private final SimpMessagingTemplate messagingTemplate;
    public RoomService(RoomRepository roomrepo, SimpMessagingTemplate messagingTemplate) {
        this.roomrepo = roomrepo;
        this.messagingTemplate=messagingTemplate;
    }

    public CreateRoomRes create(@RequestBody CreateRoom req){
        String roomcode = generatecode();
        room Room = room.builder()
                .roomCode(roomcode)
                .roomName(req.getRoomName())
                .build();
        roomrepo.save(Room);
        return  new CreateRoomRes(Room.getRoomCode(),Room.getRoomName());

    }

    public String join(@RequestBody JoinRoom req){
        room Room = roomrepo.findByRoomCode(req.getRoomCode());
        messagingTemplate.convertAndSend(
                "/topic/rooms/" + req.getRoomCode() + "/presence",
                new RoomPresenceEvent("joined", req.getRoomCode(), req.getCustomName())
        );
        return Room.getRoomName();
    }

    public void endsession(@RequestParam String roomCode){
        room Room = roomrepo.findByRoomCode(roomCode);
        roomrepo.delete(Room);
    }

    public void leave(@RequestParam String roomCode, @RequestParam String name){
        messagingTemplate.convertAndSend("/topic/rooms/" + roomCode + "/absence", new RoomPresenceEvent("LEFT", roomCode, name));
    }

    public String generatecode(){
        String roomCode;
        do {
            StringBuilder code = new StringBuilder(6);
            for (int index = 0; index < 6; index++) {
                code.append(ALPHANUMERIC.charAt(RANDOM.nextInt(ALPHANUMERIC.length())));
            }
            roomCode = code.toString();
        } while (roomrepo.existsByRoomCode(roomCode));
        return roomCode;
    }
}
