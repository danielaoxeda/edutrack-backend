package com.rodrigomv.edutrackbackend.persistence.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "docente")
public class Docente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_docente")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "codigo_docente", length = 20, nullable = false, unique = true)
    private String codigoDocente;

    @Column(name = "especialidad", length = 100, nullable = false)
    private String especialidad;

    @OneToMany(mappedBy = "docente")
    private List<DocenteSeccion> docenteSecciones = new ArrayList<>();

    public Docente() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getCodigoDocente() {
        return codigoDocente;
    }

    public void setCodigoDocente(String codigoDocente) {
        this.codigoDocente = codigoDocente;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public List<DocenteSeccion> getDocenteSecciones() {
        return docenteSecciones;
    }

    public void setDocenteSecciones(List<DocenteSeccion> docenteSecciones) {
        this.docenteSecciones = docenteSecciones;
    }
}
