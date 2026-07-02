package com.rodrigomv.edutrackbackend.dto.seccion;

public record SeccionResponseDTO(
        Long id,
        Long cursoId,
        String cursoCodigo,
        Long periodoAcademicoId,
        String periodoNombre,
        String nombre,
        Integer capacidad
) {
}
