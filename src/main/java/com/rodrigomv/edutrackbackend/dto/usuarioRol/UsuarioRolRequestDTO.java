package com.rodrigomv.edutrackbackend.dto.usuarioRol;

import jakarta.validation.constraints.NotNull;

public record UsuarioRolRequestDTO(
        @NotNull Long usuarioId,
        @NotNull Long rolId
) {
}
