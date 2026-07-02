package com.rodrigomv.edutrackbackend.dto.asistencia;

import com.rodrigomv.edutrackbackend.persistence.enums.AsistenciaEstado;

public record AsistenciaResponseDTO(
        Long id,
        Long sesionClaseId,
        String sesionTema,
        Long matriculaId,
        String estudianteCodigo,
        String seccionNombre,
        AsistenciaEstado estado,
        Boolean justificada
) {
}
