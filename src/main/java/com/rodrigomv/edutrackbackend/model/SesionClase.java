package com.rodrigomv.edutrackbackend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "sesion_clase")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SesionClase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sesion")
    private Long id;

    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seccion_id")
    private Seccion seccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creado_por")
    private Usuario creadoPor;

    @OneToMany(mappedBy = "sesion")
    private Set<Asistencia> asistencias;

}
