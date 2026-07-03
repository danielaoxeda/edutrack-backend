package com.rodrigomv.edutrackbackend.security;

import com.rodrigomv.edutrackbackend.persistence.entity.Usuario;
import com.rodrigomv.edutrackbackend.persistence.entity.UsuarioRol;
import com.rodrigomv.edutrackbackend.persistence.enums.UsuarioEstado;
import com.rodrigomv.edutrackbackend.persistence.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (UsuarioRol usuarioRol : usuario.getUsuarioRoles()) {
            String roleName = normalizeRole(usuarioRol.getRol() != null ? usuarioRol.getRol().getNombre() : null);
            if (roleName != null) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + roleName));
            }
        }

        if (authorities.isEmpty() && usuario.getDocente() != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_TEACHER"));
        }

        return User.withUsername(usuario.getEmail())
                .password(usuario.getPasswordHash())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(usuario.getEstado() != UsuarioEstado.ACTIVO)
                .build();
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
