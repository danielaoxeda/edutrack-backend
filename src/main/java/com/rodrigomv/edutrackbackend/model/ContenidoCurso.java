package com.rodrigomv.edutrackbackend.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "contenido_curso")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContenidoCurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contenido")
    private Long id;

    private String titulo;

    private String tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seccion_id")
    private Seccion seccion;

    @OneToMany(mappedBy = "contenido", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ArchivoContenido> archivos;

}
