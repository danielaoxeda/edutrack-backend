package com.rodrigomv.edutrackbackend.dto.usuarioRol;

public record UsuarioRolResponseDTO(
        Long id,
        Long usuarioId,
        String usuarioEmail,
        Long rolId,
        String rolNombre
) {
}
