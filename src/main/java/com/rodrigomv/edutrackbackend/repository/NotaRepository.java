package com.rodrigomv.edutrackbackend.repository;

import com.rodrigomv.edutrackbackend.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotaRepository extends JpaRepository<Nota, Long> {
}
