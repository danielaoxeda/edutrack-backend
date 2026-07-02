package com.rodrigomv.edutrackbackend.dto.docente;

import com.rodrigomv.edutrackbackend.dto.usuario.UsuarioRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DocenteRequestDTO(
        @Valid UsuarioRequestDTO usuario,
        @NotBlank @Size(max = 20) String codigoDocente,
        @NotBlank @Size(max = 100) String especialidad
) {
}
