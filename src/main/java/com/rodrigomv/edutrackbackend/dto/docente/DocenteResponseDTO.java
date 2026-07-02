package com.rodrigomv.edutrackbackend.dto.docente;

import com.rodrigomv.edutrackbackend.dto.usuario.UsuarioResponseDTO;

public record DocenteResponseDTO(
        Long id,
        UsuarioResponseDTO usuario,
        String codigoDocente,
        String especialidad
) {
}
