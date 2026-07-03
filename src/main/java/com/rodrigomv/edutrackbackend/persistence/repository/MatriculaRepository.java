package com.rodrigomv.edutrackbackend.persistence.repository;

import com.rodrigomv.edutrackbackend.persistence.entity.Matricula;
import com.rodrigomv.edutrackbackend.persistence.enums.MatriculaEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    
    List<Matricula> findByEstudianteId(Long estudianteId);
    
    List<Matricula> findBySeccionId(Long seccionId);
    
    List<Matricula> findByEstado(MatriculaEstado estado);
    
    boolean existsByEstudianteIdAndSeccionId(Long estudianteId, Long seccionId);

    boolean existsByEstudianteId(Long estudianteId);
    
    List<Matricula> findByEstudianteIdAndSeccionId(Long estudianteId, Long seccionId);
}
