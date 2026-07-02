package com.rodrigomv.edutrackbackend.persistence.repository;

import com.rodrigomv.edutrackbackend.persistence.entity.Estudiante;
import com.rodrigomv.edutrackbackend.persistence.enums.EstadoAcademico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    
    Optional<Estudiante> findByCodigoEstudiante(String codigoEstudiante);
    
    boolean existsByCodigoEstudiante(String codigoEstudiante);
    
    List<Estudiante> findByEstadoAcademico(EstadoAcademico estadoAcademico);
}