package com.rodrigomv.edutrackbackend.dto.sesionClase;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record SesionClaseRequestDTO(
        @NotNull Long semanaAcademicaId,
        @NotBlank @Size(max = 150) String tema,
        @NotNull LocalDate fecha
) {
}
