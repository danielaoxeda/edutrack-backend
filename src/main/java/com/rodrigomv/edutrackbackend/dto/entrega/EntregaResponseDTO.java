package com.rodrigomv.edutrackbackend.dto.entrega;

import com.rodrigomv.edutrackbackend.persistence.enums.EntregaEstado;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EntregaResponseDTO(
        Long id,
        Long actividadId,
        String actividadTitulo,
        Long matriculaId,
        String estudianteCodigo,
        String seccionNombre,
        String comentarioAlumno,
        String archivoUrl,
        LocalDateTime fechaEntrega,
        EntregaEstado estado,
        BigDecimal nota,
        String comentarioDocente
) {
}
