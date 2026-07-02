package com.rodrigomv.edutrackbackend.dto.alertaAcademica;

import com.rodrigomv.edutrackbackend.persistence.enums.AlertaTipo;
import jakarta.validation.constraints.NotNull;

public record AlertaAcademicaRequestDTO(
        @NotNull Long matriculaId,
        @NotNull AlertaTipo tipo,
        String descripcion
) {
}
