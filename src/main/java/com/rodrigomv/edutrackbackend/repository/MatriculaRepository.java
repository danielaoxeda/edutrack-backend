package com.rodrigomv.edutrackbackend.repository;

import com.rodrigomv.edutrackbackend.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    List<Matricula> findByEstudianteId(Long estudianteId);
}
