package com.rodrigomv.edutrackbackend.dto.semanaAcademica;

public record SemanaAcademicaResponseDTO(
        Long id,
        Long seccionId,
        String seccionNombre,
        Integer numeroSemana,
        String titulo
) {
}
