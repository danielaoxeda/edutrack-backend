package com.rodrigomv.edutrackbackend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "entrega_tarea")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntregaTarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entrega")
    private Long id;

    private String estado;

    private Integer intento;

    @Column(name = "archivo_url")
    private String archivoUrl;

    @Column(name = "fecha_entrega")
    private LocalDateTime fechaEntrega;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tarea_id")
    private Tarea tarea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id")
    private Usuario estudiante;

    @OneToOne(mappedBy = "entrega")
    private CalificacionTarea calificacion;

}
