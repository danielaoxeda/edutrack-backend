package com.rodrigomv.edutrackbackend.dto.alertaAcademica;

import com.rodrigomv.edutrackbackend.persistence.enums.AlertaTipo;

import java.time.LocalDateTime;

public record AlertaAcademicaResponseDTO(
        Long id,
        Long matriculaId,
        String estudianteCodigo,
        String seccionNombre,
        AlertaTipo tipo,
        String descripcion,
        LocalDateTime fechaAlerta
) {
}
