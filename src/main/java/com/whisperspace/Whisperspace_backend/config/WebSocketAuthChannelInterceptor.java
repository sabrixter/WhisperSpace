package com.whisperspace.Whisperspace_backend.config;

import com.whisperspace.Whisperspace_backend.auth.service.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;

@Component
public class WebSocketAuthChannelInterceptor implements ChannelInterceptor {
    private final JwtService jwtService;

    public WebSocketAuthChannelInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (StompCommand.CONNECT.equals(accessor.getCommand()) || StompCommand.SEND.equals(accessor.getCommand())) {
            Principal currentUser = accessor.getUser();
            if (currentUser == null) {
                extractBearerToken(accessor)
                        .flatMap(jwtService::parseToken)
                        .ifPresent(principal -> accessor.setUser(new UsernamePasswordAuthenticationToken(
                                principal,
                                null,
                                AuthorityUtils.NO_AUTHORITIES
                        )));
            }
        }
        return message;
    }

    private java.util.Optional<String> extractBearerToken(StompHeaderAccessor accessor) {
        List<String> authorizationHeaders = accessor.getNativeHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeaders == null || authorizationHeaders.isEmpty()) {
            return java.util.Optional.empty();
        }
        String header = authorizationHeaders.getFirst();
        if (header == null || !header.startsWith("Bearer ")) {
            return java.util.Optional.empty();
        }
        return java.util.Optional.of(header.substring(7));
    }
}
