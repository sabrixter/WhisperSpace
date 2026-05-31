package com.whisperspace.Whisperspace_backend.conversation.service;

import com.whisperspace.Whisperspace_backend.auth.entity.user;
import com.whisperspace.Whisperspace_backend.auth.repository.AuthRepository;
import com.whisperspace.Whisperspace_backend.auth.service.JwtService;
import com.whisperspace.Whisperspace_backend.conversation.dto.ConvoPresenceRes;
import com.whisperspace.Whisperspace_backend.conversation.dto.ConvoReqRes;
import com.whisperspace.Whisperspace_backend.conversation.dto.ConvoRes;
import com.whisperspace.Whisperspace_backend.conversation.dto.NewConvoreq;
import com.whisperspace.Whisperspace_backend.conversation.entity.ConversationParticipants;
import com.whisperspace.Whisperspace_backend.conversation.entity.Conversations;
import com.whisperspace.Whisperspace_backend.conversation.repository.ConversationParticipantRepo;
import com.whisperspace.Whisperspace_backend.conversation.repository.ConvoRepo;
import com.whisperspace.Whisperspace_backend.room.dto.RoomPresenceEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationParticipantRepo partirepo;
    private final ConvoRepo convorepo;
    private final AuthRepository authrepo;
    private final SimpMessagingTemplate messagingTemplate;
    public List<ConvoRes> findAll() {
        Long User = getCurrentUserId();
        List<Conversations> convos = convorepo.findAllUserConversations(User);

        return  convos.stream()
                .map(convo -> {

                    ConvoRes res = new ConvoRes();

                    res.setConvoid(convo.getId());
                    res.setName(convo.getName());
                    user owner = authrepo.findById(convo.getCreatedByUserId()).orElseThrow();
                    res.setCreatedBy(owner.getUsername());
                    res.setCreatedAt(convo.getCreatedAt());

                    // participants usernames
                    List<String> participants = convo.getParticipants()
                            .stream()
                            .map(p -> p.getUser().getUsername())
                            .toList();

                    res.setParticipants(participants);

                    return res;

                })
                .toList();
    }
    public ConvoRes convodetails(Long convoId) {
        Conversations convo = convorepo.findById(convoId).orElseThrow();
        ConvoRes res = new ConvoRes();
        res.setName(convo.getName());
        user owner = authrepo.findById(convo.getCreatedByUserId()).orElseThrow();
        res.setCreatedBy(owner.getUsername());
        res.setCreatedAt(convo.getCreatedAt());
        List<String> participants = convo.getParticipants()
                .stream()
                .map(p -> p.getUser().getUsername())
                .toList();

        res.setParticipants(participants);
        return res;
    }

    public Conversations createConvo(NewConvoreq req) {
        Long currentUserId = getCurrentUserId();
        LocalDateTime now = LocalDateTime.now();

        Conversations conversation = convorepo.save(
                Conversations.builder()
                        .name(req.getName())
                        .createdByUserId(currentUserId)
                        .createdAt(now)
                        .participants(new ArrayList<>())
                        .build()
        );

        List<ConversationParticipants> participants = req.getParticipants().stream()
                .map(username -> {

                    user u = authrepo.findByUsernameIgnoreCase(username);

                    if (u == null) {
                        throw new RuntimeException("User not found: " + username);
                    }

                    boolean isCreator = u.getId().equals(currentUserId);

                    return ConversationParticipants.builder()
                            .conversation(conversation)
                            .user(u)
                            .status(isCreator ? "ACCEPTED" : "PENDING")
                            .joinedAt(isCreator ? now : null)
                            .build();
                })
                .toList();

        partirepo.saveAll(participants);

        conversation.setParticipants(participants);
        convorepo.save(conversation);

        return conversation;
    }

    public List<ConvoReqRes> getrequests()
    {
        Long User = getCurrentUserId();
        user thisuser = authrepo.findById(User).orElseThrow(() -> new RuntimeException("User not found"));
        List<ConversationParticipants> requests = partirepo.findAllConversationParticipants((thisuser));

        return requests.stream()
                .map(reqs -> {
                    ConvoReqRes res = new ConvoReqRes();

                    res.setConvoname(reqs.getConversation().getName());
                    res.setConvoid(reqs.getConversation().getId());
                    user requser = authrepo.findById(reqs.getConversation().getCreatedByUserId()).orElseThrow(() -> new RuntimeException("User not found"));
                    res.setCreatedby(requser.getUsername());

                    return res;
                })
                .toList();
    }
    public void acceptRequest(@RequestParam Long convoid){
        Long User = getCurrentUserId();
        user thisuser = authrepo.findById(User).orElseThrow();
        Conversations convo = convorepo.findById(convoid).orElseThrow(() -> new RuntimeException("Conversation not found"));
        ConversationParticipants participant = partirepo.findConversationParticipants(convo,thisuser);

        participant.setStatus("ACCEPTED");
        partirepo.save(participant);
        messagingTemplate.convertAndSend(
                "/topic/rooms/" + convoid + "/presence",
                new ConvoPresenceRes(thisuser.getUsername(), "joined", convoid)
        );
    }

    public void declineRequest(@RequestParam Long convoid){
        Long User = getCurrentUserId();
        user thisuser = authrepo.findById(User).orElseThrow();
        Conversations convo = convorepo.findById(convoid).orElseThrow(() -> new RuntimeException("Conversation not found"));
        ConversationParticipants participant = partirepo.findConversationParticipants(convo,thisuser);

        participant.setStatus("DECLINED");
        partirepo.save(participant);
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof JwtService.JwtPrincipal principal)) {
            throw new RuntimeException("Authentication is null");
        }
        return principal.userId();
    }
}
