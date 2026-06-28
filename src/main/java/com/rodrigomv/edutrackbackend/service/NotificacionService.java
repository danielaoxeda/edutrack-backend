package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.persistence.entity.Notificacion;
import com.rodrigomv.edutrackbackend.persistence.repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificacionService {
    
    private final NotificacionRepository notificacionRepository;
    
    public List<Notificacion> findAll() {
        return notificacionRepository.findAll();
    }
    
    public Optional<Notificacion> findById(Long id) {
        return notificacionRepository.findById(id);
    }
    
    public List<Notificacion> findByUsuario(Long usuarioId) {
        return notificacionRepository.findByUsuarioIdOrderByFechaEnvioDesc(usuarioId);
    }
    
    public List<Notificacion> findNoLeidas(Long usuarioId) {
        return notificacionRepository.findByUsuarioIdAndLeidoFalse(usuarioId);
    }
    
    public long countNoLeidas(Long usuarioId) {
        return notificacionRepository.countByUsuarioIdAndLeidoFalse(usuarioId);
    }
    
    public Notificacion save(Notificacion notificacion) {
        if (notificacion.getFechaEnvio() == null) {
            notificacion.setFechaEnvio(LocalDateTime.now());
        }
        return notificacionRepository.save(notificacion);
    }
    
    public Notificacion update(Long id, Notificacion notificacion) {
        notificacion.setId(id);
        return notificacionRepository.save(notificacion);
    }
    
    public void delete(Long id) {
        notificacionRepository.deleteById(id);
    }
    
    public Notificacion marcarLeida(Long id) {
        Notificacion notificacion = notificacionRepository.findById(id).orElseThrow();
        notificacion.setLeido(true);
        return notificacionRepository.save(notificacion);
    }
}