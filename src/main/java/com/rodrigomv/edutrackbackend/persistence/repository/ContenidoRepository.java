package com.rodrigomv.edutrackbackend.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rodrigomv.edutrackbackend.persistence.entity.Contenido;

public interface ContenidoRepository extends JpaRepository<Contenido, Long> {
}
