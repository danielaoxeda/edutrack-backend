package com.rodrigomv.edutrackbackend.dto.rolPermiso;

public record RolPermisoResponseDTO(
        Long id,
        Long rolId,
        String rolNombre,
        Long permisoId,
        String permisoNombre
) {
}
