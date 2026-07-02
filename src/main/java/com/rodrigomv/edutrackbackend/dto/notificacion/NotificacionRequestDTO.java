package com.rodrigomv.edutrackbackend.dto.notificacion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NotificacionRequestDTO(
        @NotNull Long usuarioId,
        @NotBlank @Size(max = 100) String titulo,
        String mensaje,
        Boolean leido
) {
}
