package com.whisperspace.Whisperspace_backend.conversation.controller;

import com.whisperspace.Whisperspace_backend.conversation.dto.ConvoReqRes;
import com.whisperspace.Whisperspace_backend.conversation.dto.ConvoRes;
import com.whisperspace.Whisperspace_backend.conversation.dto.NewConvoreq;
import com.whisperspace.Whisperspace_backend.conversation.entity.Conversations;
import com.whisperspace.Whisperspace_backend.conversation.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {
    @Autowired
    private ConversationService conversationService;

    @PostMapping
    public Conversations createConversation(@RequestBody NewConvoreq request) {
        System.out.println("Creating convo");
        return (conversationService.createConvo(request));
    }

    @GetMapping
    public List<ConvoRes> getConversations() {
        return conversationService.findAll();
    }

    @GetMapping("/requests")
    public List<ConvoReqRes> getRequests() {
        return conversationService.getrequests();
    }

    @GetMapping("/{convoid}")
    public ConvoRes details(@PathVariable("convoid") Long convoid) {
        return conversationService.convodetails(convoid);
    }
    
    @PatchMapping("/accept_request")
    public void acceptMessageRequest(@RequestParam Long convoid){
        System.out.println("Accepting request");
        conversationService.acceptRequest(convoid);
    }

    @PatchMapping("/decline_request")
    public void declineMessageRequest(@RequestParam Long convoid){
        conversationService.declineRequest(convoid);
    }
}
