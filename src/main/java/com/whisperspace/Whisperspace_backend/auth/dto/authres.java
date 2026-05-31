package com.whisperspace.Whisperspace_backend.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class authres {
    private String token;
    private long id;
    private String username;
    private String email;
    private boolean online;
}
