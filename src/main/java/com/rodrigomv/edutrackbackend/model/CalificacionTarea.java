package com.rodrigomv.edutrackbackend.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "calificacion_tarea")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalificacionTarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_calificacion")
    private Long id;

    private BigDecimal nota;

    private String feedback;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "entrega_id", unique = true)
    private EntregaTarea entrega;

}
