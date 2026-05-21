package com.rodrigomv.edutrackbackend.persistence.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "subsanacion")
public class Subsanacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_subsanacion")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_entrega", nullable = false)
    private Entrega entrega;

    @Column(name = "nota_anterior", precision = 5, scale = 2)
    private BigDecimal notaAnterior;

    @Column(name = "nueva_nota", precision = 5, scale = 2)
    private BigDecimal nuevaNota;

    @Column(name = "motivo", columnDefinition = "TEXT")
    private String motivo;

    @Column(name = "fecha_subsanacion", nullable = false)
    private LocalDateTime fechaSubsanacion;

    public Subsanacion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Entrega getEntrega() {
        return entrega;
    }

    public void setEntrega(Entrega entrega) {
        this.entrega = entrega;
    }

    public BigDecimal getNotaAnterior() {
        return notaAnterior;
    }

    public void setNotaAnterior(BigDecimal notaAnterior) {
        this.notaAnterior = notaAnterior;
    }

    public BigDecimal getNuevaNota() {
        return nuevaNota;
    }

    public void setNuevaNota(BigDecimal nuevaNota) {
        this.nuevaNota = nuevaNota;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDateTime getFechaSubsanacion() {
        return fechaSubsanacion;
    }

    public void setFechaSubsanacion(LocalDateTime fechaSubsanacion) {
        this.fechaSubsanacion = fechaSubsanacion;
    }
}
