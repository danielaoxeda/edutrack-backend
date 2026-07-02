package com.rodrigomv.edutrackbackend.persistence.repository;

import com.rodrigomv.edutrackbackend.persistence.entity.Contenido;
import com.rodrigomv.edutrackbackend.persistence.enums.ContenidoTipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContenidoRepository extends JpaRepository<Contenido, Long> {
    
    List<Contenido> findBySemanaAcademicaId(Long semanaId);
    
    List<Contenido> findByTipo(ContenidoTipo tipo);
    
    List<Contenido> findByVisibleTrue();
}