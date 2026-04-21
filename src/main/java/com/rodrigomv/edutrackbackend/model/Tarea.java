package com.rodrigomv.edutrackbackend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "tarea")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarea")
    private Long id;

    private String titulo;

    private String descripcion;

    @Column(name = "fecha_limite")
    private LocalDateTime fechaLimite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seccion_id")
    private Seccion seccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creado_por")
    private Usuario creadoPor;

    @OneToMany(mappedBy = "tarea", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EntregaTarea> entregas;

}
