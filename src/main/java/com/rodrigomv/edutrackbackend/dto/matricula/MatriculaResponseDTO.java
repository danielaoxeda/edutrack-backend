package com.rodrigomv.edutrackbackend.dto.matricula;

import com.rodrigomv.edutrackbackend.persistence.enums.MatriculaEstado;

import java.time.LocalDateTime;

public record MatriculaResponseDTO(
        Long id,
        Long estudianteId,
        String estudianteCodigo,
        Long seccionId,
        String seccionNombre,
        LocalDateTime fechaMatricula,
        MatriculaEstado estado
) {
}
