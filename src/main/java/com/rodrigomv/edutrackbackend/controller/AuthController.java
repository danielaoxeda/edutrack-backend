package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.dto.auth.AuthLoginRequestDTO;
import com.rodrigomv.edutrackbackend.dto.auth.AuthLoginResponseDTO;
import com.rodrigomv.edutrackbackend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthLoginResponseDTO> login(@RequestBody AuthLoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request.email(), request.password()));
    }
}
