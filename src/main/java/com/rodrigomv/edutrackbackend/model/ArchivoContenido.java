package com.rodrigomv.edutrackbackend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "archivo_contenido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArchivoContenido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_archivo")
    private Long id;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contenido_id")
    private ContenidoCurso contenido;

}
