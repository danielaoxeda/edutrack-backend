package com.rodrigomv.edutrackbackend.dto.matricula;

import com.rodrigomv.edutrackbackend.persistence.enums.MatriculaEstado;
import jakarta.validation.constraints.NotNull;

public record MatriculaRequestDTO(
        @NotNull Long estudianteId,
        @NotNull Long seccionId,
        MatriculaEstado estado
) {
}
