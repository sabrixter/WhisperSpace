package com.whisperspace.Whisperspace_backend.Chat.controller;

import com.whisperspace.Whisperspace_backend.Chat.dto.Chatdto;
import com.whisperspace.Whisperspace_backend.Chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;
    @GetMapping
    public List<Chatdto> getallchats(@RequestParam Long convoid){
        return chatService.getconvochats(convoid);
    }
}
