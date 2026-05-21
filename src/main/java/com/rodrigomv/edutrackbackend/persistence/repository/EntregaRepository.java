package com.rodrigomv.edutrackbackend.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rodrigomv.edutrackbackend.persistence.entity.Entrega;

public interface EntregaRepository extends JpaRepository<Entrega, Long> {
}
