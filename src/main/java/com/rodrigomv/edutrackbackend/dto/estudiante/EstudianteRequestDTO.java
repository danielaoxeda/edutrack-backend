package com.rodrigomv.edutrackbackend.dto.estudiante;

import com.rodrigomv.edutrackbackend.dto.usuario.UsuarioRequestDTO;
import com.rodrigomv.edutrackbackend.persistence.enums.EstadoAcademico;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EstudianteRequestDTO(
        @Valid UsuarioRequestDTO usuario,
        @NotBlank @Size(max = 20) String codigoEstudiante,
        EstadoAcademico estadoAcademico
) {
}
