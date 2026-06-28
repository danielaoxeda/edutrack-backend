package com.rodrigomv.edutrackbackend.persistence.repository;

import com.rodrigomv.edutrackbackend.persistence.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    
    List<Notificacion> findByUsuarioIdOrderByFechaEnvioDesc(Long usuarioId);
    
    List<Notificacion> findByUsuarioIdAndLeidoFalse(Long usuarioId);
    
    long countByUsuarioIdAndLeidoFalse(Long usuarioId);
}