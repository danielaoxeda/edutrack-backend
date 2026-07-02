package com.rodrigomv.edutrackbackend.dto.asistencia;

import com.rodrigomv.edutrackbackend.persistence.enums.AsistenciaEstado;
import jakarta.validation.constraints.NotNull;

public record AsistenciaRequestDTO(
        @NotNull Long sesionClaseId,
        @NotNull Long matriculaId,
        @NotNull AsistenciaEstado estado,
        @NotNull Boolean justificada
) {
}
