package com.whisperspace.Whisperspace_backend.auth.dto;

import com.whisperspace.Whisperspace_backend.auth.entity.user;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLookupResponse {
    private Long id;
    private String username;
    private String email;

    public static UserLookupResponse from(user User) {
        return new UserLookupResponse(User.getId(), User.getUsername(), User.getEmail());
    }
}