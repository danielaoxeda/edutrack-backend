package com.rodrigomv.edutrackbackend.repository;

import com.rodrigomv.edutrackbackend.model.TokenSesion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenSesionRepository extends JpaRepository<TokenSesion, Long> {
}
