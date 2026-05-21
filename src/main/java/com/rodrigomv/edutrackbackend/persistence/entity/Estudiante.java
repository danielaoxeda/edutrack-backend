package com.rodrigomv.edutrackbackend.persistence.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import com.rodrigomv.edutrackbackend.persistence.enums.EstadoAcademico;

@Entity
@Table(name = "estudiante")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estudiante")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "codigo_estudiante", length = 20, nullable = false, unique = true)
    private String codigoEstudiante;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_academico", length = 20, nullable = false)
    private EstadoAcademico estadoAcademico;

    @OneToMany(mappedBy = "estudiante")
    private List<Matricula> matriculas = new ArrayList<>();

    public Estudiante() {
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

    public String getCodigoEstudiante() {
        return codigoEstudiante;
    }

    public void setCodigoEstudiante(String codigoEstudiante) {
        this.codigoEstudiante = codigoEstudiante;
    }

    public EstadoAcademico getEstadoAcademico() {
        return estadoAcademico;
    }

    public void setEstadoAcademico(EstadoAcademico estadoAcademico) {
        this.estadoAcademico = estadoAcademico;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }
}
