package com.rodrigomv.edutrackbackend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "docente_seccion", uniqueConstraints = @UniqueConstraint(columnNames = {"docente_id", "seccion_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocenteSeccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_docente_seccion")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "docente_id")
    private Usuario docente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seccion_id")
    private Seccion seccion;

}
