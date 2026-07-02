package com.rodrigomv.edutrackbackend.dto.subsanacion;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record SubsanacionRequestDTO(
        @NotNull Long entregaId,
        BigDecimal notaAnterior,
        BigDecimal nuevaNota,
        String motivo
) {
}
