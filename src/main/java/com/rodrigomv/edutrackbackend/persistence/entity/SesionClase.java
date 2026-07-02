package com.rodrigomv.edutrackbackend.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sesion_clase")
public class SesionClase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sesion")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_semana", nullable = false)
    private SemanaAcademica semanaAcademica;

    @Column(name = "tema", length = 150, nullable = false)
    private String tema;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @OneToMany(mappedBy = "sesionClase")
    private List<Asistencia> asistencias = new ArrayList<>();

    public SesionClase() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SemanaAcademica getSemanaAcademica() {
        return semanaAcademica;
    }

    public void setSemanaAcademica(SemanaAcademica semanaAcademica) {
        this.semanaAcademica = semanaAcademica;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public List<Asistencia> getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(List<Asistencia> asistencias) {
        this.asistencias = asistencias;
    }
}
