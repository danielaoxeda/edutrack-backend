package com.rodrigomv.edutrackbackend.dto.subsanacion;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SubsanacionResponseDTO(
        Long id,
        Long entregaId,
        Long actividadId,
        String actividadTitulo,
        Long estudianteId,
        String estudianteCodigo,
        BigDecimal notaAnterior,
        BigDecimal nuevaNota,
        String motivo,
        LocalDateTime fechaSubsanacion
) {
}
