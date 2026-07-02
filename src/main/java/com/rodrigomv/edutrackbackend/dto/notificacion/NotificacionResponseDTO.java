package com.rodrigomv.edutrackbackend.dto.notificacion;

import java.time.LocalDateTime;

public record NotificacionResponseDTO(
        Long id,
        Long usuarioId,
        String usuarioEmail,
        String titulo,
        String mensaje,
        Boolean leido,
        LocalDateTime fechaEnvio
) {
}
