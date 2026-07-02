package com.rodrigomv.edutrackbackend.dto.criterioEvaluacion;

import java.math.BigDecimal;

public record CriterioEvaluacionResponseDTO(
        Long id,
        Long seccionId,
        String seccionNombre,
        String nombre,
        BigDecimal porcentaje
) {
}
