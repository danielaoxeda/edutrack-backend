package com.rodrigomv.edutrackbackend.dto.foroRespuesta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ForoRespuestaRequestDTO(
        @NotNull Long foroId,
        @NotNull Long usuarioId,
        @NotBlank String mensaje
) {
}
