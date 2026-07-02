package com.rodrigomv.edutrackbackend.dto.actividad;

import com.rodrigomv.edutrackbackend.persistence.enums.ActividadTipo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ActividadResponseDTO(
        Long id,
        Long semanaAcademicaId,
        String semanaTitulo,
        Long criterioEvaluacionId,
        String criterioNombre,
        String titulo,
        String descripcion,
        ActividadTipo tipo,
        LocalDateTime fechaLimite,
        Boolean calificada,
        BigDecimal notaMaxima,
        Boolean visible
) {
}
