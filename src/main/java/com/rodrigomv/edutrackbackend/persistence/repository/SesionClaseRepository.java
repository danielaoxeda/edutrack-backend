package com.rodrigomv.edutrackbackend.persistence.repository;

import com.rodrigomv.edutrackbackend.persistence.entity.SesionClase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SesionClaseRepository extends JpaRepository<SesionClase, Long> {
    
    List<SesionClase> findBySemanaAcademicaId(Long semanaId);
    
    List<SesionClase> findByFecha(LocalDate fecha);
    
    List<SesionClase> findByFechaBetween(LocalDate inicio, LocalDate fin);
}