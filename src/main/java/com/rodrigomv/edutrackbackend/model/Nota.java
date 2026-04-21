package com.rodrigomv.edutrackbackend.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "nota", uniqueConstraints = @UniqueConstraint(columnNames = {"evaluacion_id", "estudiante_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nota")
    private Long id;

    private BigDecimal valor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluacion_id")
    private Evaluacion evaluacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id")
    private Usuario estudiante;

}
