package com.rodrigomv.edutrackbackend.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "seccion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_seccion")
    private Long id;

    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "periodo_id")
    private PeriodoAcademico periodo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creado_por")
    private Usuario creadoPor;

    @OneToMany(mappedBy = "seccion", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DocenteSeccion> docenteSecciones;

    @OneToMany(mappedBy = "seccion", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Matricula> matriculas;

    @OneToMany(mappedBy = "seccion", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Evaluacion> evaluaciones;

    @OneToMany(mappedBy = "seccion", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Tarea> tareas;

    @OneToMany(mappedBy = "seccion", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SesionClase> sesiones;

    @OneToMany(mappedBy = "seccion", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ContenidoCurso> contenidos;

    @OneToMany(mappedBy = "seccion", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Foro> foros;

}
