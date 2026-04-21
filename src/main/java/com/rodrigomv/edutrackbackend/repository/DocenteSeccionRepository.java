package com.rodrigomv.edutrackbackend.repository;

import com.rodrigomv.edutrackbackend.model.DocenteSeccion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DocenteSeccionRepository extends JpaRepository<DocenteSeccion, Long> {
    List<DocenteSeccion> findByDocenteId(Long docenteId);
}
