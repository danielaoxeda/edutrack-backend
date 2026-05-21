package com.rodrigomv.edutrackbackend.persistence.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "seccion")
public class Seccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_seccion")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_curso", nullable = false)
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "id_periodo", nullable = false)
    private PeriodoAcademico periodoAcademico;

    @Column(name = "nombre", length = 20, nullable = false)
    private String nombre;

    @Column(name = "capacidad", nullable = false)
    private Integer capacidad;

    @OneToMany(mappedBy = "seccion")
    private List<DocenteSeccion> docenteSecciones = new ArrayList<>();

    @OneToMany(mappedBy = "seccion")
    private List<Matricula> matriculas = new ArrayList<>();

    @OneToMany(mappedBy = "seccion")
    private List<SemanaAcademica> semanaAcademicas = new ArrayList<>();

    public Seccion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public PeriodoAcademico getPeriodoAcademico() {
        return periodoAcademico;
    }

    public void setPeriodoAcademico(PeriodoAcademico periodoAcademico) {
        this.periodoAcademico = periodoAcademico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public List<DocenteSeccion> getDocenteSecciones() {
        return docenteSecciones;
    }

    public void setDocenteSecciones(List<DocenteSeccion> docenteSecciones) {
        this.docenteSecciones = docenteSecciones;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }

    public List<SemanaAcademica> getSemanaAcademicas() {
        return semanaAcademicas;
    }

    public void setSemanaAcademicas(List<SemanaAcademica> semanaAcademicas) {
        this.semanaAcademicas = semanaAcademicas;
    }
}
