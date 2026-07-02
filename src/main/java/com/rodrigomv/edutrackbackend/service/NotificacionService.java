package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.dto.notificacion.NotificacionRequestDTO;
import com.rodrigomv.edutrackbackend.dto.notificacion.NotificacionResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.entity.Notificacion;
import com.rodrigomv.edutrackbackend.persistence.entity.Usuario;
import com.rodrigomv.edutrackbackend.persistence.repository.NotificacionRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificacionService {
    
    private final NotificacionRepository notificacionRepository;
    private final UsuarioRepository usuarioRepository;
    
    public List<NotificacionResponseDTO> findAll() {
        return notificacionRepository.findAll().stream().map(this::toResponse).toList();
    }
    
    public Optional<NotificacionResponseDTO> findById(Long id) {
        return notificacionRepository.findById(id).map(this::toResponse);
    }
    
    public List<NotificacionResponseDTO> findByUsuario(Long usuarioId) {
        return notificacionRepository.findByUsuarioIdOrderByFechaEnvioDesc(usuarioId).stream().map(this::toResponse).toList();
    }
    
    public List<NotificacionResponseDTO> findNoLeidas(Long usuarioId) {
        return notificacionRepository.findByUsuarioIdAndLeidoFalse(usuarioId).stream().map(this::toResponse).toList();
    }
    
    public long countNoLeidas(Long usuarioId) {
        return notificacionRepository.countByUsuarioIdAndLeidoFalse(usuarioId);
    }
    
    public NotificacionResponseDTO save(NotificacionRequestDTO request) {
        Notificacion notificacion = new Notificacion();
        applyRequest(notificacion, request);
        return toResponse(notificacionRepository.save(notificacion));
    }
    
    public NotificacionResponseDTO update(Long id, NotificacionRequestDTO request) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Notificación no encontrada"));
        applyRequest(notificacion, request);
        return toResponse(notificacionRepository.save(notificacion));
    }
    
    public void delete(Long id) {
        notificacionRepository.deleteById(id);
    }
    
    public NotificacionResponseDTO marcarLeida(Long id) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Notificación no encontrada"));
        notificacion.setLeido(true);
        return toResponse(notificacionRepository.save(notificacion));
    }

    private void applyRequest(Notificacion notificacion, NotificacionRequestDTO request) {
        Usuario usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        notificacion.setUsuario(usuario);
        notificacion.setTitulo(request.titulo());
        notificacion.setMensaje(request.mensaje());
        notificacion.setLeido(request.leido() != null ? request.leido() : (notificacion.getLeido() != null ? notificacion.getLeido() : false));
        if (notificacion.getFechaEnvio() == null) {
            notificacion.setFechaEnvio(LocalDateTime.now());
        }
    }

    private NotificacionResponseDTO toResponse(Notificacion notificacion) {
        return new NotificacionResponseDTO(
                notificacion.getId(),
                notificacion.getUsuario() != null ? notificacion.getUsuario().getId() : null,
                notificacion.getUsuario() != null ? notificacion.getUsuario().getEmail() : null,
                notificacion.getTitulo(),
                notificacion.getMensaje(),
                notificacion.getLeido(),
                notificacion.getFechaEnvio()
        );
    }
}