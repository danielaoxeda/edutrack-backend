package com.rodrigomv.edutrackbackend.repository;

import com.rodrigomv.edutrackbackend.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
