package com.rodrigomv.edutrackbackend.dto.foro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ForoRequestDTO(
        @NotNull Long semanaAcademicaId,
        @NotNull Long creadoPorId,
        @NotBlank @Size(max = 150) String titulo,
        String mensajePrincipal
) {
}
