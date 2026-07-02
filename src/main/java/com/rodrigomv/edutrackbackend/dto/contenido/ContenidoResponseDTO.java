package com.rodrigomv.edutrackbackend.dto.contenido;

import com.rodrigomv.edutrackbackend.persistence.enums.ContenidoTipo;

import java.time.LocalDateTime;

public record ContenidoResponseDTO(
        Long id,
        Long semanaAcademicaId,
        String semanaTitulo,
        String titulo,
        String descripcion,
        ContenidoTipo tipo,
        String urlRecurso,
        Boolean visible,
        LocalDateTime fechaPublicacion
) {
}
