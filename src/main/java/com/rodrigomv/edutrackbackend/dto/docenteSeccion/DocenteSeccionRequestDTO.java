package com.rodrigomv.edutrackbackend.dto.docenteSeccion;

import jakarta.validation.constraints.NotNull;

public record DocenteSeccionRequestDTO(
        @NotNull Long docenteId,
        @NotNull Long seccionId
) {
}
