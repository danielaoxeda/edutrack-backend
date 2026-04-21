package com.rodrigomv.edutrackbackend.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "foro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Foro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_foro")
    private Long id;

    private String titulo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seccion_id")
    private Seccion seccion;

    @OneToMany(mappedBy = "foro")
    private Set<MensajeForo> mensajes;

}
