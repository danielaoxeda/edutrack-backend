package com.rodrigomv.edutrackbackend.dto.criterioEvaluacion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CriterioEvaluacionRequestDTO(
        @NotNull Long seccionId,
        @NotBlank @Size(max = 50) String nombre,
        @NotNull BigDecimal porcentaje
) {
}
