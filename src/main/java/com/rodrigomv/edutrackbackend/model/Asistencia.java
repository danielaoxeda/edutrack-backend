package com.rodrigomv.edutrackbackend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "asistencia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asistencia")
    private Long id;

    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sesion_id")
    private SesionClase sesion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id")
    private Usuario estudiante;

}
