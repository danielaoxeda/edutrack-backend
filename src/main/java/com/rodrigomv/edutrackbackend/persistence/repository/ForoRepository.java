package com.rodrigomv.edutrackbackend.persistence.repository;

import com.rodrigomv.edutrackbackend.persistence.entity.Foro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForoRepository extends JpaRepository<Foro, Long> {
    
    List<Foro> findBySemanaAcademicaId(Long semanaId);
    
    List<Foro> findByCreadoPorId(Long usuarioId);
}