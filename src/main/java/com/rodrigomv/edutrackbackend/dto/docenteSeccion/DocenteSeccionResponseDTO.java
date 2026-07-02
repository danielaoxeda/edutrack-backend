package com.rodrigomv.edutrackbackend.dto.docenteSeccion;

public record DocenteSeccionResponseDTO(
        Long id,
        Long docenteId,
        String docenteCodigo,
        Long seccionId,
        String seccionNombre
) {
}
