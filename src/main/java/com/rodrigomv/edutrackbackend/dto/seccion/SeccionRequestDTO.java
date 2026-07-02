package com.rodrigomv.edutrackbackend.dto.seccion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SeccionRequestDTO(
        @NotNull Long cursoId,
        @NotNull Long periodoAcademicoId,
        @NotBlank @Size(max = 20) String nombre,
        @NotNull Integer capacidad
) {
}
