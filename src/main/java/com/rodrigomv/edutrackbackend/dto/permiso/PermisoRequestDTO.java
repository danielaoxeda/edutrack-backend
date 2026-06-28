package com.rodrigomv.edutrackbackend.dto.permiso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PermisoRequestDTO {

    @NotBlank
    @Size(max = 100)
    private String nombre;

    @NotBlank
    @Size(max = 100)
    private String recurso;

    @NotBlank
    @Size(max = 50)
    private String accion;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRecurso() {
        return recurso;
    }

    public void setRecurso(String recurso) {
        this.recurso = recurso;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }
}
