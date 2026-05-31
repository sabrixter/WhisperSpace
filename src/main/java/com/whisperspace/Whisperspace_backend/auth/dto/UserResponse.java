package com.whisperspace.Whisperspace_backend.auth.dto;
import com.whisperspace.Whisperspace_backend.auth.entity.user;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private boolean online;

    public static UserResponse from(user user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.isOnline()
        );
    }
}
