package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.dto.usuario.UsuarioRequestDTO;
import com.rodrigomv.edutrackbackend.dto.usuario.UsuarioResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.entity.Usuario;
import com.rodrigomv.edutrackbackend.persistence.enums.UsuarioEstado;
import com.rodrigomv.edutrackbackend.persistence.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    
    public List<UsuarioResponseDTO> findAll() {
        return usuarioRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }
    
    public Optional<UsuarioResponseDTO> findById(Long id) {
        return usuarioRepository.findById(id).map(this::toResponse);
    }
    
    public Optional<UsuarioResponseDTO> findByEmail(String email) {
        return usuarioRepository.findByEmail(email).map(this::toResponse);
    }
    
    public List<UsuarioResponseDTO> findByEstado(UsuarioEstado estado) {
        return usuarioRepository.findByEstado(estado).stream()
                .map(this::toResponse)
                .toList();
    }
    
    public UsuarioResponseDTO save(UsuarioRequestDTO request) {
        Usuario usuario = new Usuario();
        applyRequest(usuario, request, true);
        return toResponse(usuarioRepository.save(usuario));
    }
    
    public UsuarioResponseDTO update(Long id, UsuarioRequestDTO request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        applyRequest(usuario, request, false);
        return toResponse(usuarioRepository.save(usuario));
    }
    
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }
    
    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario saveEntity(Usuario usuario) {
        if (usuario.getCreatedAt() == null) {
            usuario.setCreatedAt(LocalDateTime.now());
        }
        encodePasswordIfNeeded(usuario);
        return usuarioRepository.save(usuario);
    }

    public Usuario updateEntity(Long id, Usuario usuario) {
        Usuario current = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        copyEntityFields(current, usuario);
        return usuarioRepository.save(current);
    }

    public Optional<Usuario> findEntityById(Long id) {
        return usuarioRepository.findById(id);
    }

    private void encodePasswordIfNeeded(Usuario usuario) {
        String passwordHash = usuario.getPasswordHash();
        if (passwordHash == null || passwordHash.isBlank()) {
            return;
        }

        if (passwordHash.startsWith("$2a$") || passwordHash.startsWith("$2b$") || passwordHash.startsWith("$2y$")) {
            return;
        }

        usuario.setPasswordHash(passwordEncoder.encode(passwordHash));
    }

    private void applyRequest(Usuario usuario, UsuarioRequestDTO request, boolean creating) {
        usuario.setNombres(request.getNombres());
        usuario.setApellidos(request.getApellidos());
        usuario.setEmail(request.getEmail());
        usuario.setEstado(request.getEstado() != null ? request.getEstado() : (usuario.getEstado() != null ? usuario.getEstado() : UsuarioEstado.ACTIVO));
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            usuario.setPasswordHash(request.getPassword());
        } else if (creating) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La contraseña es obligatoria");
        }
        if (creating && usuario.getCreatedAt() == null) {
            usuario.setCreatedAt(LocalDateTime.now());
        }
        encodePasswordIfNeeded(usuario);
    }

    private void copyEntityFields(Usuario target, Usuario source) {
        target.setNombres(source.getNombres());
        target.setApellidos(source.getApellidos());
        target.setEmail(source.getEmail());
        target.setEstado(source.getEstado() != null ? source.getEstado() : target.getEstado());
        if (source.getPasswordHash() != null && !source.getPasswordHash().isBlank()) {
            target.setPasswordHash(source.getPasswordHash());
            encodePasswordIfNeeded(target);
        }
        if (target.getCreatedAt() == null) {
            target.setCreatedAt(LocalDateTime.now());
        }
    }

    private UsuarioResponseDTO toResponse(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombres(),
                usuario.getApellidos(),
                usuario.getEmail(),
                usuario.getEstado(),
                usuario.getCreatedAt()
        );
    }
}
