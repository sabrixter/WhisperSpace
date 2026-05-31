package com.whisperspace.Whisperspace_backend.auth.service;

import com.whisperspace.Whisperspace_backend.auth.entity.user;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;

@Service
public class JwtService {
    private static final String HMAC_SHA256 = "HmacSHA256";

    private final String secret;
    private final long expirationSeconds;

    public JwtService(
            @Value("${app.security.jwt.secret:change-this-secret-in-production}") String secret,
            @Value("${app.security.jwt.expiration-seconds:86400}") long expirationSeconds
    ) {
        this.secret = secret;
        this.expirationSeconds = expirationSeconds;
    }

    public String generateToken(user user) {
        long issuedAt = Instant.now().getEpochSecond();
        long expiresAt = issuedAt + expirationSeconds;
        String payload = user.getId() + ":" + user.getEmail() + ":" + expiresAt;
        String signature = sign(payload);
        return base64UrlEncode(payload) + "." + signature;
    }

    public Optional<JwtPrincipal> parseToken(String token) {
        if (token == null || token.isBlank() || !token.contains(".")) {
            return Optional.empty();
        }

        String[] parts = token.split("\\.", 2);
        String payload = new String(Base64.getUrlDecoder().decode(parts[0]), StandardCharsets.UTF_8);
        if (!sign(payload).equals(parts[1])) {
            return Optional.empty();
        }

        String[] payloadParts = payload.split(":", 3);
        if (payloadParts.length != 3) {
            return Optional.empty();
        }

        long expiresAt = Long.parseLong(payloadParts[2]);
        if (Instant.now().getEpochSecond() > expiresAt) {
            return Optional.empty();
        }

        return Optional.of(new JwtPrincipal(
                Long.parseLong(payloadParts[0]),
                payloadParts[1]
        ));
    }

    private String sign(String payload) {
        try {
            Mac mac = Mac.getInstance(HMAC_SHA256);
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HMAC_SHA256));
            byte[] digest = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to sign token", exception);
        }
    }

    private String base64UrlEncode(String value) {
        return Base64.getUrlEncoder().withoutPadding()
                .encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    public record JwtPrincipal(Long userId, String email) {
    }
}
