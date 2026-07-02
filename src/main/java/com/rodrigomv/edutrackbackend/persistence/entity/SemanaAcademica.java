package com.rodrigomv.edutrackbackend.persistence.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "semana_academica")
public class SemanaAcademica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_semana")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_seccion", nullable = false)
    private Seccion seccion;

    @Column(name = "numero_semana", nullable = false)
    private Integer numeroSemana;

    @Column(name = "titulo", length = 100, nullable = false)
    private String titulo;

    @OneToMany(mappedBy = "semanaAcademica")
    private List<Contenido> contenidos = new ArrayList<>();

    @OneToMany(mappedBy = "semanaAcademica")
    private List<Actividad> actividades = new ArrayList<>();

    @OneToMany(mappedBy = "semanaAcademica")
    private List<SesionClase> sesionesClase = new ArrayList<>();

    @OneToMany(mappedBy = "semanaAcademica")
    private List<Foro> foros = new ArrayList<>();

    public SemanaAcademica() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Seccion getSeccion() {
        return seccion;
    }

    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }

    public Integer getNumeroSemana() {
        return numeroSemana;
    }

    public void setNumeroSemana(Integer numeroSemana) {
        this.numeroSemana = numeroSemana;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Contenido> getContenidos() {
        return contenidos;
    }

    public void setContenidos(List<Contenido> contenidos) {
        this.contenidos = contenidos;
    }

    public List<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(List<Actividad> actividades) {
        this.actividades = actividades;
    }

    public List<SesionClase> getSesionesClase() {
        return sesionesClase;
    }

    public void setSesionesClase(List<SesionClase> sesionesClase) {
        this.sesionesClase = sesionesClase;
    }

    public List<Foro> getForos() {
        return foros;
    }

    public void setForos(List<Foro> foros) {
        this.foros = foros;
    }
}
