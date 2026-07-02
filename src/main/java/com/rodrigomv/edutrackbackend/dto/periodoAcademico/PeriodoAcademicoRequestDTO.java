package com.rodrigomv.edutrackbackend.dto.periodoAcademico;

import com.rodrigomv.edutrackbackend.persistence.enums.PeriodoEstado;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PeriodoAcademicoRequestDTO(
        @NotBlank @Size(max = 50) String nombre,
        @NotNull LocalDate fechaInicio,
        @NotNull LocalDate fechaFin,
        @NotNull Integer numeroSemanas,
        PeriodoEstado estado
) {
}
