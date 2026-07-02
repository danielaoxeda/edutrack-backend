package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.dto.auth.AuthLoginResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.entity.Usuario;
import com.rodrigomv.edutrackbackend.persistence.entity.UsuarioRol;
import com.rodrigomv.edutrackbackend.persistence.enums.UsuarioEstado;
import com.rodrigomv.edutrackbackend.persistence.repository.UsuarioRepository;
import com.rodrigomv.edutrackbackend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    @Transactional(readOnly = true)
    public AuthLoginResponseDTO login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        Usuario usuario = usuarioRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas"));

        if (usuario.getEstado() != UsuarioEstado.ACTIVO) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuario inactivo o bloqueado");
        }

        List<String> roles = usuario.getUsuarioRoles().stream()
                .map(UsuarioRol::getRol)
                .map(rol -> rol != null ? normalizeRole(rol.getNombre()) : null)
                .filter(role -> role != null && !role.isBlank())
                .distinct()
                .toList();

        if (roles.isEmpty() && usuario.getDocente() != null) {
            roles = List.of("TEACHER");
        }

        String primaryRole = roles.stream().findFirst().orElse("STUDENT");
        String token = jwtService.generateToken(usuario, primaryRole, roles);

        return new AuthLoginResponseDTO(
                token,
                "Bearer",
                usuario.getId(),
                usuario.getEmail(),
                usuario.getNombres() + " " + usuario.getApellidos(),
                primaryRole,
                roles
        );
    }

    private String normalizeRole(String roleName) {
        if (roleName == null || roleName.isBlank()) {
            return null;
        }

        String normalized = roleName.trim().toUpperCase();
        return switch (normalized) {
            case "DOCENTE", "TEACHER" -> "TEACHER";
            case "ADMIN", "ADMINISTRADOR" -> "ADMIN";
            case "ESTUDIANTE", "STUDENT" -> "STUDENT";
            default -> normalized;
        };
    }
}
