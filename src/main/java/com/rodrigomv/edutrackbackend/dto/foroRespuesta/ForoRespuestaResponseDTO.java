package com.rodrigomv.edutrackbackend.dto.foroRespuesta;

import java.time.LocalDateTime;

public record ForoRespuestaResponseDTO(
        Long id,
        Long foroId,
        String foroTitulo,
        Long usuarioId,
        String usuarioEmail,
        String mensaje,
        LocalDateTime fechaRespuesta
) {
}
