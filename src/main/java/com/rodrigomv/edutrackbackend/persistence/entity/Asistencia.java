package com.rodrigomv.edutrackbackend.persistence.entity;

import com.rodrigomv.edutrackbackend.persistence.enums.AsistenciaEstado;

import jakarta.persistence.*;

@Entity
@Table(name = "asistencia")
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asistencia")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_sesion", nullable = false)
    private SesionClase sesionClase;

    @ManyToOne
    @JoinColumn(name = "id_matricula", nullable = false)
    private Matricula matricula;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 20, nullable = false)
    private AsistenciaEstado estado;

    @Column(name = "justificada", nullable = false)
    private Boolean justificada;

    public Asistencia() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SesionClase getSesionClase() {
        return sesionClase;
    }

    public void setSesionClase(SesionClase sesionClase) {
        this.sesionClase = sesionClase;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public AsistenciaEstado getEstado() {
        return estado;
    }

    public void setEstado(AsistenciaEstado estado) {
        this.estado = estado;
    }

    public Boolean getJustificada() {
        return justificada;
    }

    public void setJustificada(Boolean justificada) {
        this.justificada = justificada;
    }
}
