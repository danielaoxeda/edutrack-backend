package com.rodrigomv.edutrackbackend.persistence.repository;

import com.rodrigomv.edutrackbackend.persistence.entity.PeriodoAcademico;
import com.rodrigomv.edutrackbackend.persistence.enums.PeriodoEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeriodoAcademicoRepository extends JpaRepository<PeriodoAcademico, Long> {
    
    Optional<PeriodoAcademico> findByNombre(String nombre);
    
    List<PeriodoAcademico> findByEstado(PeriodoEstado estado);
    
    @Query("SELECT p FROM PeriodoAcademico p WHERE p.estado = 'ACTIVO'")
    Optional<PeriodoAcademico> findActivo();
}