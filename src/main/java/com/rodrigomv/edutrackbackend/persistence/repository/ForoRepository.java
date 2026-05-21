package com.rodrigomv.edutrackbackend.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rodrigomv.edutrackbackend.persistence.entity.Foro;

public interface ForoRepository extends JpaRepository<Foro, Long> {
}
