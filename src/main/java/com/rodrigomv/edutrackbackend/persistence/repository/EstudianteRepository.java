package com.rodrigomv.edutrackbackend.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rodrigomv.edutrackbackend.persistence.entity.Estudiante;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
}
