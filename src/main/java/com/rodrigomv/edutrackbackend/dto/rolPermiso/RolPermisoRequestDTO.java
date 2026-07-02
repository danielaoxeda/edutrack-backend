package com.rodrigomv.edutrackbackend.dto.rolPermiso;

import jakarta.validation.constraints.NotNull;

public record RolPermisoRequestDTO(
        @NotNull Long rolId,
        @NotNull Long permisoId
) {
}
