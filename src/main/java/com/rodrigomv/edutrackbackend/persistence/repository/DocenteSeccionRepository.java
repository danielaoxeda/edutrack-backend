package com.rodrigomv.edutrackbackend.persistence.repository;

import com.rodrigomv.edutrackbackend.persistence.entity.DocenteSeccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocenteSeccionRepository extends JpaRepository<DocenteSeccion, Long> {
    
    List<DocenteSeccion> findByDocenteId(Long docenteId);
    
    List<DocenteSeccion> findBySeccionId(Long seccionId);
    
    Optional<DocenteSeccion> findByDocenteIdAndSeccionId(Long docenteId, Long seccionId);
    
    boolean existsByDocenteIdAndSeccionId(Long docenteId, Long seccionId);
}