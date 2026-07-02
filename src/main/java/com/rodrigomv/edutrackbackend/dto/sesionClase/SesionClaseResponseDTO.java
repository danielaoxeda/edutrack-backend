package com.rodrigomv.edutrackbackend.dto.sesionClase;

import java.time.LocalDate;

public record SesionClaseResponseDTO(
        Long id,
        Long semanaAcademicaId,
        String semanaTitulo,
        String tema,
        LocalDate fecha
) {
}
