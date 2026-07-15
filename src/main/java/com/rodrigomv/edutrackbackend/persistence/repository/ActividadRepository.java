package com.rodrigomv.edutrackbackend.persistence.repository;

import com.rodrigomv.edutrackbackend.persistence.entity.Actividad;
import com.rodrigomv.edutrackbackend.persistence.enums.ActividadTipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Long> {
    
    List<Actividad> findBySemanaAcademicaId(Long semanaId);
    
    List<Actividad> findByTipo(ActividadTipo tipo);
    
    List<Actividad> findByVisibleTrue();
    
    List<Actividad> findByFechaLimiteBetween(LocalDateTime inicio, LocalDateTime fin);
    
    List<Actividad> findByCriterioEvaluacionId(Long criterioId);

    List<Actividad> findBySemanaAcademicaSeccionIdInAndVisibleTrueOrderByFechaLimiteAsc(List<Long> seccionIds);
}
