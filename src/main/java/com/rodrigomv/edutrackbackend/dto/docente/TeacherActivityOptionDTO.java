package com.rodrigomv.edutrackbackend.dto.docente;

public record TeacherActivityOptionDTO(
        Long seccionId,
        String cursoCodigo,
        String cursoNombre,
        String seccionNombre
) {
}
