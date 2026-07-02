package com.rodrigomv.edutrackbackend.dto.semanaAcademica;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SemanaAcademicaRequestDTO(
        @NotNull Long seccionId,
        @NotNull Integer numeroSemana,
        @NotNull @Size(max = 100) String titulo
) {
}
