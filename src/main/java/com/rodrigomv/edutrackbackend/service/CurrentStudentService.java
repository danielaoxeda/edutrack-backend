package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.persistence.entity.Estudiante;
import com.rodrigomv.edutrackbackend.persistence.repository.EstudianteRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentStudentService {

    private final UsuarioRepository usuarioRepository;
    private final EstudianteRepository estudianteRepository;

    public Estudiante getRequiredStudent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("No hay un estudiante autenticado");
        }

        var usuario = usuarioRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new AccessDeniedException("El usuario autenticado no existe"));

        return estudianteRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new AccessDeniedException("El usuario autenticado no tiene perfil estudiante"));
    }
}
