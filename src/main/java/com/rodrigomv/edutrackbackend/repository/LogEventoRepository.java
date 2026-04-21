package com.rodrigomv.edutrackbackend.repository;

import com.rodrigomv.edutrackbackend.model.LogEvento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogEventoRepository extends JpaRepository<LogEvento, Long> {
}
