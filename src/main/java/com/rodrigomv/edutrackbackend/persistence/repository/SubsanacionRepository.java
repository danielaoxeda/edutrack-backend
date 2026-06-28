package com.rodrigomv.edutrackbackend.persistence.repository;

import com.rodrigomv.edutrackbackend.persistence.entity.Subsanacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubsanacionRepository extends JpaRepository<Subsanacion, Long> {
    
    List<Subsanacion> findByEntregaId(Long entregaId);
}