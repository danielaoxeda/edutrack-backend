package com.rodrigomv.edutrackbackend.persistence.repository;

import com.rodrigomv.edutrackbackend.persistence.entity.SemanaAcademica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SemanaAcademicaRepository extends JpaRepository<SemanaAcademica, Long> {
    
    List<SemanaAcademica> findBySeccionIdOrderByNumeroSemanaAsc(Long seccionId);
}