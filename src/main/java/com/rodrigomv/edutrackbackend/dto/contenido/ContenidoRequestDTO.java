package com.rodrigomv.edutrackbackend.dto.contenido;

import com.rodrigomv.edutrackbackend.persistence.enums.ContenidoTipo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ContenidoRequestDTO(
        @NotNull Long semanaAcademicaId,
        @NotBlank @Size(max = 150) String titulo,
        String descripcion,
        @NotNull ContenidoTipo tipo,
        String urlRecurso,
        Boolean visible
) {
}
