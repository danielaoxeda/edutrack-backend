package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.persistence.entity.Docente;
import com.rodrigomv.edutrackbackend.persistence.repository.DocenteRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.DocenteSeccionRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrentTeacherService {

    private final UsuarioRepository usuarioRepository;
    private final DocenteRepository docenteRepository;
    private final DocenteSeccionRepository docenteSeccionRepository;

    public Docente getRequiredTeacher() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("No hay un docente autenticado");
        }

        var usuario = usuarioRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new AccessDeniedException("El usuario autenticado no existe"));

        return docenteRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new AccessDeniedException("El usuario autenticado no tiene perfil docente"));
    }

    public Set<Long> getAssignedSectionIds(Docente docente) {
        return docenteSeccionRepository.findByDocenteId(docente.getId()).stream()
                .map(asignacion -> asignacion.getSeccion().getId())
                .collect(Collectors.toSet());
    }
}
