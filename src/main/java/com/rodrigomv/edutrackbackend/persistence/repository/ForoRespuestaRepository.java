package com.rodrigomv.edutrackbackend.persistence.repository;

import com.rodrigomv.edutrackbackend.persistence.entity.ForoRespuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForoRespuestaRepository extends JpaRepository<ForoRespuesta, Long> {
    
    List<ForoRespuesta> findByForoId(Long foroId);
    
    List<ForoRespuesta> findByUsuarioId(Long usuarioId);
}