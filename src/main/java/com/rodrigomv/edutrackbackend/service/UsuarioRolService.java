package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.dto.usuarioRol.UsuarioRolRequestDTO;
import com.rodrigomv.edutrackbackend.dto.usuarioRol.UsuarioRolResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.entity.Rol;
import com.rodrigomv.edutrackbackend.persistence.entity.Usuario;
import com.rodrigomv.edutrackbackend.persistence.entity.UsuarioRol;
import com.rodrigomv.edutrackbackend.persistence.repository.RolRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.UsuarioRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.UsuarioRolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioRolService {
    
    private final UsuarioRolRepository usuarioRolRepository;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    
    public List<UsuarioRolResponseDTO> findAll() {
        return usuarioRolRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }
    
    public Optional<UsuarioRolResponseDTO> findById(Long id) {
        return usuarioRolRepository.findById(id).map(this::toResponse);
    }
    
    public List<UsuarioRolResponseDTO> findByUsuario(Long usuarioId) {
        return usuarioRolRepository.findByUsuarioId(usuarioId).stream()
                .map(this::toResponse)
                .toList();
    }
    
    public List<UsuarioRolResponseDTO> findByRol(Long rolId) {
        return usuarioRolRepository.findByRolId(rolId).stream()
                .map(this::toResponse)
                .toList();
    }
    
    public Optional<UsuarioRolResponseDTO> findByUsuarioAndRol(Long usuarioId, Long rolId) {
        return usuarioRolRepository.findByUsuarioIdAndRolId(usuarioId, rolId).map(this::toResponse);
    }
    
    public UsuarioRolResponseDTO save(UsuarioRolRequestDTO request) {
        Usuario usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        Rol rol = rolRepository.findById(request.rolId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado"));

        if (usuarioRolRepository.existsByUsuarioIdAndRolId(usuario.getId(), rol.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El usuario ya tiene ese rol");
        }

        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setUsuario(usuario);
        usuarioRol.setRol(rol);
        return toResponse(usuarioRolRepository.save(usuarioRol));
    }
    
    public void delete(Long id) {
        usuarioRolRepository.deleteById(id);
    }
    
    public boolean existsByUsuarioAndRol(Long usuarioId, Long rolId) {
        return usuarioRolRepository.existsByUsuarioIdAndRolId(usuarioId, rolId);
    }

    private UsuarioRolResponseDTO toResponse(UsuarioRol usuarioRol) {
        return new UsuarioRolResponseDTO(
                usuarioRol.getId(),
                usuarioRol.getUsuario() != null ? usuarioRol.getUsuario().getId() : null,
                usuarioRol.getUsuario() != null ? usuarioRol.getUsuario().getEmail() : null,
                usuarioRol.getRol() != null ? usuarioRol.getRol().getId() : null,
                usuarioRol.getRol() != null ? usuarioRol.getRol().getNombre() : null
        );
    }
}