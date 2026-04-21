package com.rodrigomv.edutrackbackend.repository;

import com.rodrigomv.edutrackbackend.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaRepository extends JpaRepository<Tarea, Long> {
}
