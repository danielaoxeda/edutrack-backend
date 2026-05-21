package com.rodrigomv.edutrackbackend.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rodrigomv.edutrackbackend.persistence.entity.Permiso;

public interface PermisoRepository extends JpaRepository<Permiso, Long> {
}
