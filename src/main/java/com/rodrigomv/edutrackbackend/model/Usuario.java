package com.rodrigomv.edutrackbackend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "usuario", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    private String nombre;

    @Column(unique = true)
    private String email;

    private String password;

    private Boolean estado = true;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @ManyToMany
    @JoinTable(name = "usuario_rol",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> roles;

    @OneToMany(mappedBy = "creadoPor")
    private Set<PeriodoAcademico> periodosCreados;

    @OneToMany(mappedBy = "creadoPor")
    private Set<Curso> cursosCreados;

    @OneToMany(mappedBy = "creadoPor")
    private Set<Seccion> seccionesCreadas;

    @OneToMany(mappedBy = "creadoPor")
    private Set<Evaluacion> evaluacionesCreadas;

    @OneToMany(mappedBy = "creadoPor")
    private Set<Tarea> tareasCreadas;

    @OneToMany(mappedBy = "creadoPor")
    private Set<SesionClase> sesionesCreadas;

    @OneToMany(mappedBy = "docente")
    private Set<DocenteSeccion> docenteSecciones;

    @OneToMany(mappedBy = "estudiante")
    private Set<Matricula> matriculas;

    @OneToMany(mappedBy = "estudiante")
    private Set<Nota> notas;

    @OneToMany(mappedBy = "estudiante")
    private Set<EntregaTarea> entregas;

    @OneToMany(mappedBy = "estudiante")
    private Set<Asistencia> asistencias;

    @OneToMany(mappedBy = "usuario")
    private Set<MensajeForo> mensajes;

    @OneToMany(mappedBy = "usuario")
    private Set<Notificacion> notificaciones;

    @OneToMany(mappedBy = "usuario")
    private Set<TokenSesion> tokens;

    @OneToMany(mappedBy = "usuario")
    private Set<LogEvento> logs;

}
