package com.whisperspace.Whisperspace_backend.room.repository;

import com.whisperspace.Whisperspace_backend.room.entity.room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<room, Long> {
    boolean existsByRoomCode(String roomCode);

    room findByRoomCode(String roomCode);
}
