package com.rodrigomv.edutrackbackend.repository;

import com.rodrigomv.edutrackbackend.model.Seccion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SeccionRepository extends JpaRepository<Seccion, Long> {
    List<Seccion> findByCursoId(Long cursoId);
}
