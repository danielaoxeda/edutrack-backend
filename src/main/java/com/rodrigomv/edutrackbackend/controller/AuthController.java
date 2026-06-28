package com.rodrigomv.edutrackbackend.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginRequest {
        private String email;
        private String password;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginResponse {
        private String token;
        private String email;
        private String name;
        private String role;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String email = request.getEmail().toLowerCase();
        String role = "STUDENT";
        String name = "Adriano Bautista";

        if (email.contains("admin") || email.contains("administrador")) {
            role = "ADMIN";
            name = "Admin EduTrack";
        } else if (email.contains("teacher") || email.contains("docente") || email.contains("profesor")) {
            role = "TEACHER";
            name = "Dr. Roberto M. - Docente Titular";
        }

        LoginResponse response = new LoginResponse(
                "mock-jwt-token-xyz-123456",
                request.getEmail(),
                name,
                role
        );

        return ResponseEntity.ok(response);
    }
}
