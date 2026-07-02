package com.rodrigomv.edutrackbackend.persistence.repository;

import com.rodrigomv.edutrackbackend.persistence.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    
    Optional<Curso> findByCodigo(String codigo);
    
    boolean existsByCodigo(String codigo);
}