package com.rodrigomv.edutrackbackend.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "foro")
public class Foro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_foro")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_semana", nullable = false)
    private SemanaAcademica semanaAcademica;

    @Column(name = "titulo", length = 150, nullable = false)
    private String titulo;

    @Column(name = "mensaje_principal", columnDefinition = "TEXT")
    private String mensajePrincipal;

    @ManyToOne
    @JoinColumn(name = "creado_por", nullable = false)
    private Usuario creadoPor;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "foro")
    private List<ForoRespuesta> foroRespuestas = new ArrayList<>();

    public Foro() {
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensajePrincipal() {
        return mensajePrincipal;
    }

    public void setMensajePrincipal(String mensajePrincipal) {
        this.mensajePrincipal = mensajePrincipal;
    }

    public Usuario getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Usuario creadoPor) {
        this.creadoPor = creadoPor;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<ForoRespuesta> getForoRespuestas() {
        return foroRespuestas;
    }

    public void setForoRespuestas(List<ForoRespuesta> foroRespuestas) {
        this.foroRespuestas = foroRespuestas;
    }
}
