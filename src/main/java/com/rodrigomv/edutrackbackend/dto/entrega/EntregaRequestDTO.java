package com.rodrigomv.edutrackbackend.dto.entrega;

import com.rodrigomv.edutrackbackend.persistence.enums.EntregaEstado;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record EntregaRequestDTO(
        @NotNull Long actividadId,
        @NotNull Long matriculaId,
        String comentarioAlumno,
        String archivoUrl,
        EntregaEstado estado,
        BigDecimal nota,
        String comentarioDocente
) {
}
