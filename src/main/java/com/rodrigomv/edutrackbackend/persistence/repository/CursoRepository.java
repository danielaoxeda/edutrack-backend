package com.rodrigomv.edutrackbackend.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rodrigomv.edutrackbackend.persistence.entity.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
