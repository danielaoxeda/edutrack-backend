package com.rodrigomv.edutrackbackend.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.rodrigomv.edutrackbackend.persistence.enums.MatriculaEstado;

@Entity
@Table(name = "matricula")
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_matricula")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_estudiante", nullable = false)
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "id_seccion", nullable = false)
    private Seccion seccion;

    @Column(name = "fecha_matricula", nullable = false)
    private LocalDateTime fechaMatricula;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 20, nullable = false)
    private MatriculaEstado estado;

    @OneToMany(mappedBy = "matricula")
    private List<Entrega> entregas = new ArrayList<>();

    @OneToMany(mappedBy = "matricula")
    private List<Asistencia> asistencias = new ArrayList<>();

    @OneToMany(mappedBy = "matricula")
    private List<AlertaAcademica> alertasAcademicas = new ArrayList<>();

    public Matricula() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Seccion getSeccion() {
        return seccion;
    }

    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }

    public LocalDateTime getFechaMatricula() {
        return fechaMatricula;
    }

    public void setFechaMatricula(LocalDateTime fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }

    public MatriculaEstado getEstado() {
        return estado;
    }

    public void setEstado(MatriculaEstado estado) {
        this.estado = estado;
    }

    public List<Entrega> getEntregas() {
        return entregas;
    }

    public void setEntregas(List<Entrega> entregas) {
        this.entregas = entregas;
    }

    public List<Asistencia> getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(List<Asistencia> asistencias) {
        this.asistencias = asistencias;
    }

    public List<AlertaAcademica> getAlertasAcademicas() {
        return alertasAcademicas;
    }

    public void setAlertasAcademicas(List<AlertaAcademica> alertasAcademicas) {
        this.alertasAcademicas = alertasAcademicas;
    }
}
