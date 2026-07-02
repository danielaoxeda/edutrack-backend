package com.rodrigomv.edutrackbackend.dto.curso;

public record CursoResponseDTO(
        Long id,
        String codigo,
        String nombre,
        String descripcion,
        Integer creditos
) {
}
