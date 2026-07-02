package com.rodrigomv.edutrackbackend.dto.estudiante;

import com.rodrigomv.edutrackbackend.dto.usuario.UsuarioResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.enums.EstadoAcademico;

public record EstudianteResponseDTO(
        Long id,
        UsuarioResponseDTO usuario,
        String codigoEstudiante,
        EstadoAcademico estadoAcademico
) {
}
