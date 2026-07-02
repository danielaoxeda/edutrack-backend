package com.rodrigomv.edutrackbackend.dto.foro;

import java.time.LocalDateTime;

public record ForoResponseDTO(
        Long id,
        Long semanaAcademicaId,
        String semanaTitulo,
        Long creadoPorId,
        String creadoPorEmail,
        String titulo,
        String mensajePrincipal,
        LocalDateTime fechaCreacion
) {
}
