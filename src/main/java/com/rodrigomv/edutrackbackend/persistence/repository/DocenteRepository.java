package com.rodrigomv.edutrackbackend.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rodrigomv.edutrackbackend.persistence.entity.Docente;

public interface DocenteRepository extends JpaRepository<Docente, Long> {
}
