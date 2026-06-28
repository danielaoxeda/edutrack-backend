package com.rodrigomv.edutrackbackend.persistence.repository;

import com.rodrigomv.edutrackbackend.persistence.entity.Entrega;
import com.rodrigomv.edutrackbackend.persistence.enums.EntregaEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Long> {
    
    List<Entrega> findByActividadId(Long actividadId);
    
    List<Entrega> findByMatriculaId(Long matriculaId);
    
    List<Entrega> findByEstado(EntregaEstado estado);
    
    Optional<Entrega> findByActividadIdAndMatriculaId(Long actividadId, Long matriculaId);
    
    boolean existsByActividadIdAndMatriculaId(Long actividadId, Long matriculaId);
}