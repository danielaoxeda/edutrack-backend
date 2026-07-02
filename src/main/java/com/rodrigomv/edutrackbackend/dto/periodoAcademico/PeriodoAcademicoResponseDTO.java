package com.rodrigomv.edutrackbackend.dto.periodoAcademico;

import com.rodrigomv.edutrackbackend.persistence.enums.PeriodoEstado;

import java.time.LocalDate;

public record PeriodoAcademicoResponseDTO(
        Long id,
        String nombre,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        Integer numeroSemanas,
        PeriodoEstado estado
) {
}
