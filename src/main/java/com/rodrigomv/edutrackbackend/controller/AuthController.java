package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.persistence.entity.Usuario;
import com.rodrigomv.edutrackbackend.persistence.repository.UsuarioRepository;
import com.rodrigomv.edutrackbackend.security.JwtService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UsuarioRepository usuarioRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
    }

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
        private String nombres;
        private String apellidos;
        private String role;
        private Long userId;
        private long expiresIn;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);

        // Obtener datos del usuario
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Obtener el primer rol (para简化)
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_ESTUDIANTE")
                .replace("ROLE_", "");

        LoginResponse response = new LoginResponse(
                token,
                usuario.getEmail(),
                usuario.getNombres(),
                usuario.getApellidos(),
                role,
                usuario.getId(),
                jwtService.getExpirationTime() / 1000 // segundos
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Token no proporcionado");
        }

        String token = authHeader.substring(7);
        String email = jwtService.extractUsername(token);

        if (email != null) {
            return ResponseEntity.ok("Token válido para: " + email);
        }

        return ResponseEntity.badRequest().body("Token inválido");
    }
}
