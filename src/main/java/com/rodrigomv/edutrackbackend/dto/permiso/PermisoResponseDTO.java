package com.rodrigomv.edutrackbackend.dto.permiso;

public class PermisoResponseDTO {

    private Long id;
    private String nombre;
    private String recurso;
    private String accion;

    public PermisoResponseDTO() {
    }

    public PermisoResponseDTO(Long id, String nombre, String recurso, String accion) {
        this.id = id;
        this.nombre = nombre;
        this.recurso = recurso;
        this.accion = accion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
