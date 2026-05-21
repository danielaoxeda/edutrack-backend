package com.rodrigomv.edutrackbackend.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rodrigomv.edutrackbackend.persistence.entity.Notificacion;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
}
