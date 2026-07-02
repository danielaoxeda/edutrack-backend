package com.rodrigomv.edutrackbackend.dto.auth;

import java.util.List;

public record AuthLoginResponseDTO(
        String token,
        String tokenType,
        Long userId,
        String email,
        String name,
        String role,
        List<String> roles
) {
}
