package com.rodrigomv.edutrackbackend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.math.BigDecimal;

@Entity
@Table(name = "evaluacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evaluacion")
    private Long id;

    private String nombre;

    private String tipo;

    private BigDecimal peso;

    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seccion_id")
    private Seccion seccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creado_por")
    private Usuario creadoPor;

    @OneToMany(mappedBy = "evaluacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Nota> notas;

}
