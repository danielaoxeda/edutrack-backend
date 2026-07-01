package com.rodrigomv.edutrackbackend.config;

import com.rodrigomv.edutrackbackend.persistence.entity.*;
import com.rodrigomv.edutrackbackend.persistence.enums.*;
import com.rodrigomv.edutrackbackend.persistence.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DataSeeder - Carga datos de prueba para desarrollo y testing
 * Se activa solo con el perfil "dev" o "seed"
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataSeeder {

    // ==================== REPOSITORIES ====================
    private final PasswordEncoder passwordEncoder;
    private final RolRepository rolRepository;
    private final PermisoRepository permisoRepository;
    private final RolPermisoRepository rolPermisoRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final DocenteRepository docenteRepository;
    private final EstudianteRepository estudianteRepository;
    private final PeriodoAcademicoRepository periodoRepository;
    private final CursoRepository cursoRepository;
    private final SeccionRepository seccionRepository;
    private final DocenteSeccionRepository docenteSeccionRepository;
    private final CriterioEvaluacionRepository criterioRepository;
    private final SemanaAcademicaRepository semanaRepository;
    private final SesionClaseRepository sesionRepository;
    private final MatriculaRepository matriculaRepository;
    private final AsistenciaRepository asistenciaRepository;
    private final ActividadRepository actividadRepository;
    private final EntregaRepository entregaRepository;
    private final NotificacionRepository notificacionRepository;

    @Bean
    @Profile({"dev", "seed", "mysql"})
    public CommandLineRunner seedData() {
        return args -> {
            if (usuarioRepository.count() > 0) {
                log.info("Base de datos ya tiene datos. Saltando seeder.");
                return;
            }

            log.info("=== INICIANDO DATA SEEDER ===");

            // 1. ROLES
            log.info("Creando roles...");
            Rol admin = createRol("ADMIN");
            Rol docente = createRol("DOCENTE");
            Rol estudiante = createRol("ESTUDIANTE");

            // 2. PERMISOS
            log.info("Creando permisos...");
            Permiso pUsuarios = createPermiso("usuarios", "leer");
            Permiso pUsuariosEscribir = createPermiso("usuarios", "escribir");
            Permiso pCursos = createPermiso("cursos", "leer");
            Permiso pCursosEscribir = createPermiso("cursos", "escribir");
            Permiso pNotas = createPermiso("notas", "leer");
            Permiso pNotasEscribir = createPermiso("notas", "escribir");
            Permiso pAsistencia = createPermiso("asistencia", "leer");
            Permiso pAsistenciaEscribir = createPermiso("asistencia", "escribir");

            // 3. ASIGNAR PERMISOS A ROLES
            log.info("Asignando permisos a roles...");
            createRolPermiso(admin, pUsuarios);
            createRolPermiso(admin, pUsuariosEscribir);
            createRolPermiso(admin, pCursos);
            createRolPermiso(admin, pCursosEscribir);
            createRolPermiso(docente, pCursos);
            createRolPermiso(docente, pNotas);
            createRolPermiso(docente, pNotasEscribir);
            createRolPermiso(docente, pAsistencia);
            createRolPermiso(docente, pAsistenciaEscribir);
            createRolPermiso(estudiante, pCursos);
            createRolPermiso(estudiante, pNotas);
            createRolPermiso(estudiante, pAsistencia);

            // 4. USUARIOS
            log.info("Creando usuarios...");

            // Admin
            Usuario adminUser = createUsuario(
                "Admin", "EduTrack", "admin@edutrack.edu", "password123", UsuarioEstado.ACTIVO
            );
            createUsuarioRol(adminUser, admin);

            // Docentes
            Usuario docente1User = createUsuario(
                "Roberto", "Martinez", "roberto.martinez@edutrack.edu", "password123", UsuarioEstado.ACTIVO
            );
            Usuario docente2User = createUsuario(
                "Ana", "Silva", "ana.silva@edutrack.edu", "password123", UsuarioEstado.ACTIVO
            );
            Usuario docente3User = createUsuario(
                "Carlos", "Gomez", "carlos.gomez@edutrack.edu", "password123", UsuarioEstado.ACTIVO
            );

            createUsuarioRol(docente1User, docente);
            createUsuarioRol(docente2User, docente);
            createUsuarioRol(docente3User, docente);

            // Estudiantes
            List<Usuario> estudiantesUsuarios = List.of(
                createUsuario("Valeria", "Castillo", "vcastillo@edutrack.edu", "password123", UsuarioEstado.ACTIVO),
                createUsuario("Mateo", "Rojas", "mrojas@edutrack.edu", "password123", UsuarioEstado.ACTIVO),
                createUsuario("Carlos", "Mendoza", "cmendoza@edutrack.edu", "password123", UsuarioEstado.ACTIVO),
                createUsuario("Ana", "Rojas", "arojas@edutrack.edu", "password123", UsuarioEstado.ACTIVO),
                createUsuario("Luis", "Pena", "lpena@edutrack.edu", "password123", UsuarioEstado.ACTIVO),
                createUsuario("Lucia", "Mendez", "lmendez@edutrack.edu", "password123", UsuarioEstado.ACTIVO),
                createUsuario("Sofia", "Castro", "scastro@edutrack.edu", "password123", UsuarioEstado.ACTIVO),
                createUsuario("Diego", "Torres", "dtorres@edutrack.edu", "password123", UsuarioEstado.ACTIVO),
                createUsuario("Gabriel", "Ruiz", "gruiz@edutrack.edu", "password123", UsuarioEstado.ACTIVO),
                createUsuario("Valentina", "Gomez", "vgomez@edutrack.edu", "password123", UsuarioEstado.ACTIVO)
            );

            for (Usuario u : estudiantesUsuarios) {
                createUsuarioRol(u, estudiante);
            }

            // 5. DOCENTES
            log.info("Creando docentes...");
            Docente docente1 = createDocente(docente1User, "DOC-001", "Ingenieria de Software");
            Docente docente2 = createDocente(docente2User, "DOC-002", "Base de Datos");
            Docente docente3 = createDocente(docente3User, "DOC-003", "Inteligencia Artificial");

            // 6. ESTUDIANTES
            log.info("Creando estudiantes...");
            List<Estudiante> estudiantes = List.of(
                createEstudiante(estudiantesUsuarios.get(0), "2023-0145", EstadoAcademico.REGULAR),
                createEstudiante(estudiantesUsuarios.get(1), "2023-0211", EstadoAcademico.REGULAR),
                createEstudiante(estudiantesUsuarios.get(2), "2023-0089", EstadoAcademico.OBSERVADO),
                createEstudiante(estudiantesUsuarios.get(3), "2023-0301", EstadoAcademico.REGULAR),
                createEstudiante(estudiantesUsuarios.get(4), "2023-0412", EstadoAcademico.REGULAR),
                createEstudiante(estudiantesUsuarios.get(5), "2023-0182", EstadoAcademico.REGULAR),
                createEstudiante(estudiantesUsuarios.get(6), "2023-0523", EstadoAcademico.REGULAR),
                createEstudiante(estudiantesUsuarios.get(7), "2023-0091", EstadoAcademico.CONDICIONADO),
                createEstudiante(estudiantesUsuarios.get(8), "2023-0341", EstadoAcademico.REGULAR),
                createEstudiante(estudiantesUsuarios.get(9), "2023-0112", EstadoAcademico.REGULAR)
            );

            // 7. PERIODO ACADEMICO
            log.info("Creando periodo academico...");
            PeriodoAcademico periodo = createPeriodo(
                "2026-1",
                LocalDate.of(2026, 3, 1),
                LocalDate.of(2026, 7, 31),
                17,
                PeriodoEstado.ACTIVO
            );

            // 8. CURSOS
            log.info("Creando cursos...");
            Curso curso1 = createCurso("ISW-401", "Ingenieria de Software III", "Metodologias agiles y patrones de diseño", 4);
            Curso curso2 = createCurso("BBD-302", "Base de Datos II", "SQL avanzado y administracion de BD", 3);
            Curso curso3 = createCurso("INT-501", "Inteligencia Artificial", "Machine Learning y redes neuronales", 4);
            Curso curso4 = createCurso("SOP-205", "Sistemas Operativos", "Gestion de procesos y memoria", 3);

            // 9. SECCIONES
            log.info("Creando secciones...");
            Seccion seccion1 = createSeccion(curso1, periodo, "Grupo A", 35);
            Seccion seccion2 = createSeccion(curso2, periodo, "Grupo B", 30);
            Seccion seccion3 = createSeccion(curso3, periodo, "Grupo A", 25);
            Seccion seccion4 = createSeccion(curso4, periodo, "Grupo B", 32);

            // 10. DOCENTE-SECCION (asignar docentes)
            log.info("Asignando docentes a secciones...");
            createDocenteSeccion(docente1, seccion1);
            createDocenteSeccion(docente2, seccion2);
            createDocenteSeccion(docente3, seccion3);
            createDocenteSeccion(docente1, seccion4);

            // 11. CRITERIOS DE EVALUACION
            log.info("Creando criterios de evaluacion...");
            createCriterio(seccion1, "Practicas Calificadas", new BigDecimal("30.00"));
            createCriterio(seccion1, "Examen Parcial", new BigDecimal("30.00"));
            createCriterio(seccion1, "Examen Final", new BigDecimal("25.00"));
            createCriterio(seccion1, "Proyecto", new BigDecimal("15.00"));

            createCriterio(seccion2, "Practicas de Laboratorio", new BigDecimal("40.00"));
            createCriterio(seccion2, "Examen Parcial", new BigDecimal("30.00"));
            createCriterio(seccion2, "Examen Final", new BigDecimal("30.00"));

            createCriterio(seccion3, "Practicas", new BigDecimal("25.00"));
            createCriterio(seccion3, "Proyecto de Investigacion", new BigDecimal("35.00"));
            createCriterio(seccion3, "Examen Final", new BigDecimal("40.00"));

            // 12. SEMANAS ACADEMICAS
            log.info("Creando semanas academicas...");
            List<SemanaAcademica> semanasSeccion1 = createSemanasSeccion(seccion1, 4);
            List<SemanaAcademica> semanasSeccion2 = createSemanasSeccion(seccion2, 4);

            // 13. SESIONES DE CLASE
            log.info("Creando sesiones de clase...");
            LocalDate fechaBase = LocalDate.of(2026, 6, 1);

            // Seccion 1 - 3 sesiones por semana
            createSesion(semanasSeccion1.get(0), "Introduccion a patrones de diseño", fechaBase);
            createSesion(semanasSeccion1.get(0), "Patron Singleton y Factory", fechaBase.plusDays(2));
            createSesion(semanasSeccion1.get(0), "Patron Observer", fechaBase.plusDays(4));

            createSesion(semanasSeccion1.get(1), "Patron MVC", fechaBase.plusDays(7));
            createSesion(semanasSeccion1.get(1), "Patron Repository", fechaBase.plusDays(9));
            createSesion(semanasSeccion1.get(1), "Practica Dirigida", fechaBase.plusDays(11));

            createSesion(semanasSeccion1.get(2), "Patron Strategy", fechaBase.plusDays(14));
            createSesion(semanasSeccion1.get(2), "Patron Decorator", fechaBase.plusDays(16));
            createSesion(semanasSeccion1.get(2), "Laboratorio", fechaBase.plusDays(18));

            // Seccion 2 - 2 sesiones por semana
            createSesion(semanasSeccion2.get(0), "SQL Avanzado - Joins", fechaBase);
            createSesion(semanasSeccion2.get(0), "Subconsultas y Vistas", fechaBase.plusDays(3));

            createSesion(semanasSeccion2.get(1), "Procedimientos Almacenados", fechaBase.plusDays(7));
            createSesion(semanasSeccion2.get(1), "Triggers", fechaBase.plusDays(10));

            // 14. MATRICULAS
            log.info("Creando matriculas...");
            // Seccion 1 - 6 estudiantes
            Matricula m1 = createMatricula(estudiantes.get(0), seccion1, MatriculaEstado.ACTIVO);
            Matricula m2 = createMatricula(estudiantes.get(1), seccion1, MatriculaEstado.ACTIVO);
            Matricula m3 = createMatricula(estudiantes.get(2), seccion1, MatriculaEstado.ACTIVO);
            Matricula m4 = createMatricula(estudiantes.get(6), seccion1, MatriculaEstado.ACTIVO);
            Matricula m5 = createMatricula(estudiantes.get(8), seccion1, MatriculaEstado.ACTIVO);
            Matricula m6 = createMatricula(estudiantes.get(9), seccion1, MatriculaEstado.ACTIVO);

            // Seccion 2 - 4 estudiantes
            Matricula m7 = createMatricula(estudiantes.get(1), seccion2, MatriculaEstado.ACTIVO);
            Matricula m8 = createMatricula(estudiantes.get(3), seccion2, MatriculaEstado.ACTIVO);
            Matricula m9 = createMatricula(estudiantes.get(4), seccion2, MatriculaEstado.ACTIVO);
            Matricula m10 = createMatricula(estudiantes.get(5), seccion2, MatriculaEstado.ACTIVO);

            // Seccion 3 - 3 estudiantes
            Matricula m11 = createMatricula(estudiantes.get(0), seccion3, MatriculaEstado.ACTIVO);
            Matricula m12 = createMatricula(estudiantes.get(6), seccion3, MatriculaEstado.ACTIVO);
            Matricula m13 = createMatricula(estudiantes.get(8), seccion3, MatriculaEstado.ACTIVO);

            // 15. ASISTENCIAS
            log.info("Creando assistencias...");
            List<SesionClase> sesiones1 = sesionRepository.findBySemanaAcademicaId(semanasSeccion1.get(0).getId());
            if (!sesiones1.isEmpty()) {
                SesionClase sesion = sesiones1.get(0);
                List<Matricula> matriculasSeccion1 = matriculaRepository.findBySeccionId(seccion1.getId());

                createAsistencia(sesion, m1, AsistenciaEstado.PRESENTE, false);
                createAsistencia(sesion, m2, AsistenciaEstado.PRESENTE, false);
                createAsistencia(sesion, m3, AsistenciaEstado.TARDE, true);  // Justificada
                createAsistencia(sesion, m4, AsistenciaEstado.PRESENTE, false);
                createAsistencia(sesion, m5, AsistenciaEstado.FALTA, false);
                createAsistencia(sesion, m6, AsistenciaEstado.PRESENTE, false);
            }

            // 16. ACTIVIDADES
            log.info("Creando actividades...");
            Actividad act1 = createActividad(
                semanasSeccion1.get(0),
                null,
                "PC1: Patrones Singleton y Factory",
                "Implementar los patrones Singleton y Factory en Java",
                ActividadTipo.PC,
                LocalDateTime.of(2026, 6, 15, 23, 59),
                false,
                new BigDecimal("20.00"),
                true
            );

            Actividad act2 = createActividad(
                semanasSeccion1.get(1),
                null,
                "Practica Dirigida: Patron MVC",
                "Desarrollar una aplicacion usando MVC",
                ActividadTipo.PRACTICA,
                LocalDateTime.of(2026, 6, 22, 23, 59),
                false,
                new BigDecimal("15.00"),
                true
            );

            Actividad act3 = createActividad(
                semanasSeccion1.get(2),
                null,
                "Proyecto Final: Sistema de Gestion",
                "Proyecto integrador usando todos los patrones",
                ActividadTipo.PROYECTO,
                LocalDateTime.of(2026, 7, 15, 23, 59),
                false,
                new BigDecimal("25.00"),
                true
            );

            // 17. ENTREGAS
            log.info("Creando entregas...");
            createEntrega(
                act1, m1,
                "Entrego mi solucion con los patrones",
                "https://storage.edutrack.edu/entregas/m1-act1.zip",
                LocalDateTime.of(2026, 6, 15, 22, 30),
                EntregaEstado.ENTREGADO,
                new BigDecimal("18.50"),
                "Buen trabajo, pero falta documentacion"
            );

            createEntrega(
                act1, m2,
                "Aqui esta el codigo fuente",
                "https://storage.edutrack.edu/entregas/m2-act1.zip",
                LocalDateTime.of(2026, 6, 16, 0, 15),  // Atrasado
                EntregaEstado.ATRASADO,
                null,
                null
            );

            createEntrega(
                act1, m4,
                "Practica completada",
                "https://storage.edutrack.edu/entregas/m4-act1.zip",
                LocalDateTime.of(2026, 6, 15, 20, 0),
                EntregaEstado.ENTREGADO,
                new BigDecimal("19.00"),
                "Excelente implementacion"
            );

            // 18. NOTIFICACIONES
            log.info("Creando notificaciones...");
            createNotificacion(
                estudiantesUsuarios.get(0),
                "Nueva actividad publicada",
                "Se ha publicado la PC1 de Ingenieria de Software III",
                false
            );

            createNotificacion(
                estudiantesUsuarios.get(0),
                "Actividad calificada",
                "Tu PC1 ha sido calificada con 18.5",
                true
            );

            createNotificacion(
                estudiantesUsuarios.get(2),
                "Alerta academica",
                "Tienes falta(s) en Ingenieria de Software III",
                false
            );

            log.info("=== DATA SEEDER COMPLETADO ===");
            log.info("Usuarios creados: {}", usuarioRepository.count());
            log.info("Docentes creados: {}", docenteRepository.count());
            log.info("Estudiantes creados: {}", estudianteRepository.count());
            log.info("Cursos creados: {}", cursoRepository.count());
            log.info("Secciones creadas: {}", seccionRepository.count());
            log.info("Matriculas creadas: {}", matriculaRepository.count());
            log.info("Actividades creadas: {}", actividadRepository.count());
            log.info("Entregas creadas: {}", entregaRepository.count());
        };
    }

    // ==================== HELPER METHODS ====================

    private Rol createRol(String nombre) {
        Rol rol = new Rol();
        rol.setNombre(nombre);
        return rolRepository.save(rol);
    }

    private Permiso createPermiso(String recurso, String accion) {
        Permiso permiso = new Permiso();
        permiso.setRecurso(recurso);
        permiso.setAccion(accion);
        return permisoRepository.save(permiso);
    }

    private void createRolPermiso(Rol rol, Permiso permiso) {
        RolPermiso rp = new RolPermiso();
        rp.setRol(rol);
        rp.setPermiso(permiso);
        rolPermisoRepository.save(rp);
    }

    private Usuario createUsuario(String nombres, String apellidos, String email, String password, UsuarioEstado estado) {
        Usuario u = new Usuario();
        u.setNombres(nombres);
        u.setApellidos(apellidos);
        u.setEmail(email);
        u.setPasswordHash(passwordEncoder.encode(password)); // BCrypt hash
        u.setEstado(estado);
        u.setCreatedAt(LocalDateTime.now());
        return usuarioRepository.save(u);
    }

    private void createUsuarioRol(Usuario usuario, Rol rol) {
        UsuarioRol ur = new UsuarioRol();
        ur.setUsuario(usuario);
        ur.setRol(rol);
        usuarioRolRepository.save(ur);
    }

    private Docente createDocente(Usuario usuario, String codigo, String especialidad) {
        Docente d = new Docente();
        d.setUsuario(usuario);
        d.setCodigoDocente(codigo);
        d.setEspecialidad(especialidad);
        return docenteRepository.save(d);
    }

    private Estudiante createEstudiante(Usuario usuario, String codigo, EstadoAcademico estado) {
        Estudiante e = new Estudiante();
        e.setUsuario(usuario);
        e.setCodigoEstudiante(codigo);
        e.setEstadoAcademico(estado);
        return estudianteRepository.save(e);
    }

    private PeriodoAcademico createPeriodo(String nombre, LocalDate inicio, LocalDate fin, int semanas, PeriodoEstado estado) {
        PeriodoAcademico p = new PeriodoAcademico();
        p.setNombre(nombre);
        p.setFechaInicio(inicio);
        p.setFechaFin(fin);
        p.setNumeroSemanas(semanas);
        p.setEstado(estado);
        return periodoRepository.save(p);
    }

    private Curso createCurso(String codigo, String nombre, String descripcion, int creditos) {
        Curso c = new Curso();
        c.setCodigo(codigo);
        c.setNombre(nombre);
        c.setDescripcion(descripcion);
        c.setCreditos(creditos);
        return cursoRepository.save(c);
    }

    private Seccion createSeccion(Curso curso, PeriodoAcademico periodo, String nombre, int capacidad) {
        Seccion s = new Seccion();
        s.setCurso(curso);
        s.setPeriodoAcademico(periodo);
        s.setNombre(nombre);
        s.setCapacidad(capacidad);
        return seccionRepository.save(s);
    }

    private void createDocenteSeccion(Docente docente, Seccion seccion) {
        DocenteSeccion ds = new DocenteSeccion();
        ds.setDocente(docente);
        ds.setSeccion(seccion);
        docenteSeccionRepository.save(ds);
    }

    private CriterioEvaluacion createCriterio(Seccion seccion, String nombre, BigDecimal porcentaje) {
        CriterioEvaluacion c = new CriterioEvaluacion();
        c.setSeccion(seccion);
        c.setNombre(nombre);
        c.setPorcentaje(porcentaje);
        return criterioRepository.save(c);
    }

    private List<SemanaAcademica> createSemanasSeccion(Seccion seccion, int cantidad) {
        return java.util.stream.IntStream.rangeClosed(1, cantidad)
            .mapToObj(num -> {
                SemanaAcademica s = new SemanaAcademica();
                s.setSeccion(seccion);
                s.setNumeroSemana(num);
                s.setTitulo("Semana " + num);
                return semanaRepository.save(s);
            })
            .collect(java.util.stream.Collectors.toList());
    }

    private SesionClase createSesion(SemanaAcademica semana, String tema, LocalDate fecha) {
        SesionClase s = new SesionClase();
        s.setSemanaAcademica(semana);
        s.setTema(tema);
        s.setFecha(fecha);
        return sesionRepository.save(s);
    }

    private Matricula createMatricula(Estudiante estudiante, Seccion seccion, MatriculaEstado estado) {
        Matricula m = new Matricula();
        m.setEstudiante(estudiante);
        m.setSeccion(seccion);
        m.setFechaMatricula(LocalDateTime.now().minusDays(30));
        m.setEstado(estado);
        return matriculaRepository.save(m);
    }

    private Asistencia createAsistencia(SesionClase sesion, Matricula matricula, AsistenciaEstado estado, boolean justificada) {
        Asistencia a = new Asistencia();
        a.setSesionClase(sesion);
        a.setMatricula(matricula);
        a.setEstado(estado);
        a.setJustificada(justificada);
        return asistenciaRepository.save(a);
    }

    private Actividad createActividad(SemanaAcademica semana, CriterioEvaluacion criterio, String titulo,
                                      String descripcion, ActividadTipo tipo, LocalDateTime fechaLimite,
                                      boolean calificada, BigDecimal notaMaxima, boolean visible) {
        Actividad a = new Actividad();
        a.setSemanaAcademica(semana);
        a.setCriterioEvaluacion(criterio);
        a.setTitulo(titulo);
        a.setDescripcion(descripcion);
        a.setTipo(tipo);
        a.setFechaLimite(fechaLimite);
        a.setCalificada(calificada);
        a.setNotaMaxima(notaMaxima);
        a.setVisible(visible);
        return actividadRepository.save(a);
    }

    private Entrega createEntrega(Actividad actividad, Matricula matricula, String comentario,
                                   String archivoUrl, LocalDateTime fechaEntrega, EntregaEstado estado,
                                   BigDecimal nota, String comentarioDocente) {
        Entrega e = new Entrega();
        e.setActividad(actividad);
        e.setMatricula(matricula);
        e.setComentarioAlumno(comentario);
        e.setArchivoUrl(archivoUrl);
        e.setFechaEntrega(fechaEntrega);
        e.setEstado(estado);
        e.setNota(nota);
        e.setComentarioDocente(comentarioDocente);
        return entregaRepository.save(e);
    }

    private Notificacion createNotificacion(Usuario usuario, String titulo, String mensaje, boolean leida) {
        Notificacion n = new Notificacion();
        n.setUsuario(usuario);
        n.setTitulo(titulo);
        n.setMensaje(mensaje);
        n.setLeido(leida);
        n.setFechaEnvio(LocalDateTime.now());
        return notificacionRepository.save(n);
    }
}
