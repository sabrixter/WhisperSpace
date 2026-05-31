package com.whisperspace.Whisperspace_backend.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class registerreq {
    private String username;
    private String email;
    private String password;
}
