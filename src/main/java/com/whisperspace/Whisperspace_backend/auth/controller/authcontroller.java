package com.whisperspace.Whisperspace_backend.auth.controller;

import com.whisperspace.Whisperspace_backend.auth.dto.UserLookupResponse;
import com.whisperspace.Whisperspace_backend.auth.dto.authres;
import com.whisperspace.Whisperspace_backend.auth.dto.loginreq;
import com.whisperspace.Whisperspace_backend.auth.dto.registerreq;
import com.whisperspace.Whisperspace_backend.auth.entity.user;
import com.whisperspace.Whisperspace_backend.auth.service.authservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class authcontroller {

    private final authservice authService;

    @Autowired
    public authcontroller(authservice authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public authres register(@RequestBody registerreq request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public authres login(@RequestBody loginreq request) {
        return authService.login(request);
    }

    @GetMapping("/users/search")
    public List<user> searchUsers(@RequestParam String query) {
        System.out.println("CONTROLLER HIT with query = " + query);
        return authService.searchUsers(query);
    }
}
