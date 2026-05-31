package com.whisperspace.Whisperspace_backend.auth.service;

import com.whisperspace.Whisperspace_backend.auth.dto.UserLookupResponse;
import com.whisperspace.Whisperspace_backend.auth.dto.authres;
import com.whisperspace.Whisperspace_backend.auth.dto.loginreq;
import com.whisperspace.Whisperspace_backend.auth.dto.registerreq;
import com.whisperspace.Whisperspace_backend.auth.entity.user;
import com.whisperspace.Whisperspace_backend.auth.repository.AuthRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class authservice {

    private final AuthRepository authrepo;
    private final JwtService jwtService;
    public authservice(AuthRepository authrepo, JwtService jwtService) {
        this.authrepo = authrepo;
        this.jwtService = jwtService;
    }

    public authres register(registerreq request) {
        if (authrepo.existsByUsernameIgnoreCase(request.getUsername().trim())) {
            System.out.println("Username is already in use");
        }
        user User = user.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .createdAt(LocalDateTime.now())
                .online(false)
                .build();

        user u = authrepo.save(User);
        return new authres(jwtService.generateToken(u), u.getId(),u.getUsername(), u.getEmail(), u.isOnline());
    }

    public authres login(loginreq request) {
        user User = authrepo.findByUsernameIgnoreCase(request.getUsername());
        if(User.getPassword().equals(request.getPassword())) {
            User.setOnline(true);
            user u =  authrepo.save(User);
            return new authres(jwtService.generateToken(u),  u.getId(),u.getUsername(), u.getEmail(), u.isOnline());
        }
        return null;
    }

    public List<user> searchUsers(String query) {
        if (query == null || query.isBlank()) {
            System.out.println("Query is null or blank");
        }
        return authrepo.searchUsers(query.trim());
    }

}
