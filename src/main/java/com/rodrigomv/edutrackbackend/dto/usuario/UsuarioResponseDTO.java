package com.rodrigomv.edutrackbackend.dto.usuario;

import com.rodrigomv.edutrackbackend.persistence.enums.UsuarioEstado;

import java.time.LocalDateTime;

public class UsuarioResponseDTO {

    private Long id;
    private String nombres;
    private String apellidos;
    private String email;
    private UsuarioEstado estado;
    private LocalDateTime createdAt;

    public UsuarioResponseDTO() {
    }

    public UsuarioResponseDTO(Long id, String nombres, String apellidos, String email,
                              UsuarioEstado estado, LocalDateTime createdAt) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.estado = estado;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UsuarioEstado getEstado() {
        return estado;
    }

    public void setEstado(UsuarioEstado estado) {
        this.estado = estado;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
