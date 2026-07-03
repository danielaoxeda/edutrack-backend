package com.rodrigomv.edutrackbackend.dto.admin;

import com.rodrigomv.edutrackbackend.persistence.enums.UsuarioEstado;
import jakarta.validation.constraints.NotNull;

public record UserStatusRequestDTO(@NotNull UsuarioEstado estado) {
}
