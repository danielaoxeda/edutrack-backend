package com.rodrigomv.edutrackbackend.persistence.repository;

import com.rodrigomv.edutrackbackend.persistence.entity.AlertaAcademica;
import com.rodrigomv.edutrackbackend.persistence.enums.AlertaTipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertaAcademicaRepository extends JpaRepository<AlertaAcademica, Long> {
    
    List<AlertaAcademica> findByMatriculaId(Long matriculaId);
    
    List<AlertaAcademica> findByTipo(AlertaTipo tipo);
}