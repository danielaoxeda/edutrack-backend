package com.rodrigomv.edutrackbackend.persistence.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.rodrigomv.edutrackbackend.persistence.enums.EntregaEstado;

@Entity
@Table(name = "entrega")
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entrega")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_actividad", nullable = false)
    private Actividad actividad;

    @ManyToOne
    @JoinColumn(name = "id_matricula", nullable = false)
    private Matricula matricula;

    @Column(name = "comentario_alumno", columnDefinition = "TEXT")
    private String comentarioAlumno;

    @Column(name = "archivo_url", length = 255)
    private String archivoUrl;

    @Column(name = "fecha_entrega", nullable = false)
    private LocalDateTime fechaEntrega;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 20, nullable = false)
    private EntregaEstado estado;

    @Column(name = "nota", precision = 5, scale = 2)
    private BigDecimal nota;

    @Column(name = "comentario_docente", columnDefinition = "TEXT")
    private String comentarioDocente;

    @OneToMany(mappedBy = "entrega")
    private List<Subsanacion> subsanaciones = new ArrayList<>();

    public Entrega() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public String getComentarioAlumno() {
        return comentarioAlumno;
    }

    public void setComentarioAlumno(String comentarioAlumno) {
        this.comentarioAlumno = comentarioAlumno;
    }

    public String getArchivoUrl() {
        return archivoUrl;
    }

    public void setArchivoUrl(String archivoUrl) {
        this.archivoUrl = archivoUrl;
    }

    public LocalDateTime getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public EntregaEstado getEstado() {
        return estado;
    }

    public void setEstado(EntregaEstado estado) {
        this.estado = estado;
    }

    public BigDecimal getNota() {
        return nota;
    }

    public void setNota(BigDecimal nota) {
        this.nota = nota;
    }

    public String getComentarioDocente() {
        return comentarioDocente;
    }

    public void setComentarioDocente(String comentarioDocente) {
        this.comentarioDocente = comentarioDocente;
    }

    public List<Subsanacion> getSubsanaciones() {
        return subsanaciones;
    }

    public void setSubsanaciones(List<Subsanacion> subsanaciones) {
        this.subsanaciones = subsanaciones;
    }
}
