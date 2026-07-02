package com.rodrigomv.edutrackbackend.dto.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CursoRequestDTO(
        @NotBlank @Size(max = 20) String codigo,
        @NotBlank @Size(max = 100) String nombre,
        String descripcion,
        @NotNull Integer creditos
) {
}
