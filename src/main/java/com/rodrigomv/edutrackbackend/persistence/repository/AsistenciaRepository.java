package com.rodrigomv.edutrackbackend.persistence.repository;

import com.rodrigomv.edutrackbackend.persistence.entity.Asistencia;
import com.rodrigomv.edutrackbackend.persistence.enums.AsistenciaEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
    
    List<Asistencia> findBySesionClaseId(Long sesionId);
    
    List<Asistencia> findByMatriculaId(Long matriculaId);

    List<Asistencia> findByMatriculaIdIn(List<Long> matriculaIds);
    
    List<Asistencia> findByEstado(AsistenciaEstado estado);
    
    Optional<Asistencia> findBySesionClaseIdAndMatriculaId(Long sesionId, Long matriculaId);
}
