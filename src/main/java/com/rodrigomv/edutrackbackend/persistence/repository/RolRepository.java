package com.rodrigomv.edutrackbackend.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rodrigomv.edutrackbackend.persistence.entity.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {
}
