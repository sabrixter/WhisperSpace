package com.whisperspace.Whisperspace_backend.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class loginreq {
    private String username;
    private String password;
}
