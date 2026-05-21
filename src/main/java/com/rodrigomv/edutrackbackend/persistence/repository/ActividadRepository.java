package com.rodrigomv.edutrackbackend.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rodrigomv.edutrackbackend.persistence.entity.Actividad;

public interface ActividadRepository extends JpaRepository<Actividad, Long> {
}
