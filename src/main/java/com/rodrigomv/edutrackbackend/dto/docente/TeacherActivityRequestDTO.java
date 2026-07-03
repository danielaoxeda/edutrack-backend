package com.rodrigomv.edutrackbackend.dto.docente;

import com.rodrigomv.edutrackbackend.persistence.enums.ActividadTipo;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TeacherActivityRequestDTO(
        @NotNull Long seccionId,
        @NotNull @Min(1) @Max(24) Integer numeroSemana,
        @NotBlank @Size(max = 100) String titulo,
        String descripcion,
        @NotNull ActividadTipo tipo,
        @NotNull LocalDateTime fechaLimite,
        @NotNull Boolean calificada,
        @NotNull @DecimalMin("0.1") BigDecimal notaMaxima,
        @NotNull Boolean visible
) {
}
