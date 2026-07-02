package com.rodrigomv.edutrackbackend.dto.actividad;

import com.rodrigomv.edutrackbackend.persistence.enums.ActividadTipo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ActividadRequestDTO(
        @NotNull Long semanaAcademicaId,
        Long criterioEvaluacionId,
        @NotBlank @Size(max = 100) String titulo,
        String descripcion,
        @NotNull ActividadTipo tipo,
        @NotNull LocalDateTime fechaLimite,
        Boolean calificada,
        @NotNull BigDecimal notaMaxima,
        Boolean visible
) {
}
