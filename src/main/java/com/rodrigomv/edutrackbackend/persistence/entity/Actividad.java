package com.rodrigomv.edutrackbackend.persistence.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.rodrigomv.edutrackbackend.persistence.enums.ActividadTipo;

@Entity
@Table(name = "actividad")
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actividad")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_semana", nullable = false)
    private SemanaAcademica semanaAcademica;

    @ManyToOne
    @JoinColumn(name = "id_criterio")
    private CriterioEvaluacion criterioEvaluacion;

    @Column(name = "titulo", length = 100, nullable = false)
    private String titulo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", length = 20, nullable = false)
    private ActividadTipo tipo;

    @Column(name = "fecha_limite", nullable = false)
    private LocalDateTime fechaLimite;

    @Column(name = "calificada", nullable = false)
    private Boolean calificada;

    @Column(name = "nota_maxima", precision = 5, scale = 2, nullable = false)
    private BigDecimal notaMaxima;

    @Column(name = "visible", nullable = false)
    private Boolean visible;

    @OneToMany(mappedBy = "actividad")
    private List<Entrega> entregas = new ArrayList<>();

    public Actividad() {
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

    public CriterioEvaluacion getCriterioEvaluacion() {
        return criterioEvaluacion;
    }

    public void setCriterioEvaluacion(CriterioEvaluacion criterioEvaluacion) {
        this.criterioEvaluacion = criterioEvaluacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ActividadTipo getTipo() {
        return tipo;
    }

    public void setTipo(ActividadTipo tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDateTime fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public Boolean getCalificada() {
        return calificada;
    }

    public void setCalificada(Boolean calificada) {
        this.calificada = calificada;
    }

    public BigDecimal getNotaMaxima() {
        return notaMaxima;
    }

    public void setNotaMaxima(BigDecimal notaMaxima) {
        this.notaMaxima = notaMaxima;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public List<Entrega> getEntregas() {
        return entregas;
    }

    public void setEntregas(List<Entrega> entregas) {
        this.entregas = entregas;
    }
}
