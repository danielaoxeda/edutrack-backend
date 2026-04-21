package com.rodrigomv.edutrackbackend.repository;

import com.rodrigomv.edutrackbackend.model.MensajeForo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MensajeForoRepository extends JpaRepository<MensajeForo, Long> {
}
