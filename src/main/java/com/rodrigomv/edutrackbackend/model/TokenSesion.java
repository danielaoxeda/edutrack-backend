package com.rodrigomv.edutrackbackend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "token_sesion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenSesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_token")
    private Long id;

    private String token;

    @Column(name = "fecha_expiracion")
    private LocalDateTime fechaExpiracion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
