package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.dto.docente.DashboardDocenteResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.entity.*;
import com.rodrigomv.edutrackbackend.persistence.enums.EntregaEstado;
import com.rodrigomv.edutrackbackend.persistence.enums.EstadoAcademico;
import com.rodrigomv.edutrackbackend.persistence.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardDocenteService {

    private final CursoRepository cursoRepository;
    private final SeccionRepository seccionRepository;
    private final MatriculaRepository matriculaRepository;
    private final ActividadRepository actividadRepository;
    private final EntregaRepository entregaRepository;
    private final SemanaAcademicaRepository semanaAcademicaRepository;
    private final SesionClaseRepository sesionClaseRepository;
    private final CurrentTeacherService currentTeacherService;

    @Transactional(readOnly = true)
    public DashboardDocenteResponseDTO getDashboard() {
        Docente docente = currentTeacherService.getRequiredTeacher();
        Set<Long> sectionIds = currentTeacherService.getAssignedSectionIds(docente);

        List<Seccion> secciones = seccionRepository.findAll().stream()
                .filter(seccion -> sectionIds.contains(seccion.getId()))
                .toList();
        Set<Long> courseIds = secciones.stream()
                .map(seccion -> seccion.getCurso().getId())
                .collect(Collectors.toSet());
        List<Curso> cursos = cursoRepository.findAll().stream()
                .filter(curso -> courseIds.contains(curso.getId()))
                .toList();
        List<Matricula> matriculas = matriculaRepository.findAll().stream()
                .filter(matricula -> sectionIds.contains(matricula.getSeccion().getId()))
                .toList();
        List<SemanaAcademica> semanas = semanaAcademicaRepository.findAll().stream()
                .filter(semana -> sectionIds.contains(semana.getSeccion().getId()))
                .toList();
        Set<Long> weekIds = semanas.stream().map(SemanaAcademica::getId).collect(Collectors.toSet());
        List<Actividad> actividades = actividadRepository.findAll().stream()
                .filter(actividad -> weekIds.contains(actividad.getSemanaAcademica().getId()))
                .toList();
        Set<Long> activityIds = actividades.stream().map(Actividad::getId).collect(Collectors.toSet());
        List<Entrega> entregas = entregaRepository.findAll().stream()
                .filter(entrega -> activityIds.contains(entrega.getActividad().getId()))
                .toList();
        List<SesionClase> sesiones = sesionClaseRepository.findAll().stream()
                .filter(sesion -> weekIds.contains(sesion.getSemanaAcademica().getId()))
                .toList();

        Map<Long, List<Seccion>> seccionesPorCurso = secciones.stream()
                .collect(Collectors.groupingBy(seccion -> seccion.getCurso().getId()));
        Map<Long, List<SemanaAcademica>> semanasPorSeccion = semanas.stream()
                .collect(Collectors.groupingBy(semana -> semana.getSeccion().getId()));
        Map<Long, List<Actividad>> actividadesPorSemana = actividades.stream()
                .collect(Collectors.groupingBy(actividad -> actividad.getSemanaAcademica().getId()));
        Map<Long, List<Entrega>> entregasPorActividad = entregas.stream()
                .collect(Collectors.groupingBy(entrega -> entrega.getActividad().getId()));
        Map<Long, List<SesionClase>> sesionesPorSemana = sesiones.stream()
                .collect(Collectors.groupingBy(sesion -> sesion.getSemanaAcademica().getId()));

        List<Entrega> entregasConNota = entregas.stream()
                .filter(entrega -> entrega.getNota() != null)
                .toList();

        List<DashboardDocenteResponseDTO.StatDTO> stats = List.of(
                new DashboardDocenteResponseDTO.StatDTO(
                        "Cursos Activos",
                        cursos.size(),
                        null,
                        "BookOpen"
                ),
                new DashboardDocenteResponseDTO.StatDTO(
                        "Estudiantes Totales",
                        matriculas.stream()
                                .filter(matricula -> matricula.getEstado() != null
                                        && "ACTIVO".equalsIgnoreCase(matricula.getEstado().name()))
                                .map(matricula -> matricula.getEstudiante().getId())
                                .distinct()
                                .count(),
                        null,
                        "Users"
                ),
                new DashboardDocenteResponseDTO.StatDTO(
                        "Tareas Pendientes",
                        actividades.stream()
                                .filter(actividad -> Boolean.FALSE.equals(actividad.getCalificada()))
                                .count(),
                        null,
                        "FileText"
                ),
                new DashboardDocenteResponseDTO.StatDTO(
                        "Eval. por Revisar",
                        entregas.stream()
                                .filter(entrega -> entrega.getEstado() != EntregaEstado.REVISADO)
                                .count(),
                        null,
                        "GraduationCap"
                ),
                new DashboardDocenteResponseDTO.StatDTO(
                        "Promedio General",
                        formatAverage(entregasConNota.stream()
                                .map(Entrega::getNota)
                                .map(BigDecimal::doubleValue)
                                .mapToDouble(Double::doubleValue)
                                .average()
                                .orElse(0.0)),
                        "/ 5.0",
                        "TrendingUp"
                )
        );

        List<DashboardDocenteResponseDTO.CourseDTO> courses = cursos.stream()
                .map(curso -> buildCourseDTO(
                        curso,
                        seccionesPorCurso.getOrDefault(curso.getId(), List.of()),
                        semanasPorSeccion,
                        actividadesPorSemana,
                        entregasPorActividad,
                        sesionesPorSemana,
                        matriculas,
                        entregas
                ))
                .sorted(Comparator.comparing(DashboardDocenteResponseDTO.CourseDTO::getTitle))
                .toList();

        List<DashboardDocenteResponseDTO.PendingReviewDTO> pendingReviews = entregas.stream()
                .filter(entrega -> entrega.getEstado() != EntregaEstado.REVISADO)
                .sorted(Comparator.comparing(Entrega::getFechaEntrega).reversed())
                .limit(4)
                .map(entrega -> new DashboardDocenteResponseDTO.PendingReviewDTO(
                        "r-" + entrega.getId(),
                        getStudentName(entrega),
                        getCourseName(entrega),
                        getTaskName(entrega),
                        formatRelativeTime(entrega.getFechaEntrega())
                ))
                .toList();

        List<DashboardDocenteResponseDTO.AlertDTO> alerts = buildAlerts(cursos, matriculas, actividades, entregas);

        List<DashboardDocenteResponseDTO.ScheduleDTO> schedule = buildSchedule(sesiones, semanasPorSeccion);

        List<DashboardDocenteResponseDTO.CourseAverageDTO> averages = courses.stream()
                .map(course -> new DashboardDocenteResponseDTO.CourseAverageDTO(
                        course.getTitle(),
                        parseDouble(course.getAverageGrade())
                ))
                .sorted(Comparator.comparing(DashboardDocenteResponseDTO.CourseAverageDTO::getAverage).reversed())
                .toList();

        return new DashboardDocenteResponseDTO(stats, courses, pendingReviews, alerts, schedule, averages);
    }

    private DashboardDocenteResponseDTO.CourseDTO buildCourseDTO(
            Curso curso,
            List<Seccion> seccionesCurso,
            Map<Long, List<SemanaAcademica>> semanasPorSeccion,
            Map<Long, List<Actividad>> actividadesPorSemana,
            Map<Long, List<Entrega>> entregasPorActividad,
            Map<Long, List<SesionClase>> sesionesPorSemana,
            List<Matricula> matriculas,
            List<Entrega> entregas
    ) {
        List<Long> seccionIds = seccionesCurso.stream().map(Seccion::getId).toList();
        List<SemanaAcademica> semanasCurso = seccionIds.stream()
                .flatMap(seccionId -> semanasPorSeccion.getOrDefault(seccionId, List.of()).stream())
                .toList();
        List<Actividad> actividadesCurso = semanasCurso.stream()
                .flatMap(semana -> actividadesPorSemana.getOrDefault(semana.getId(), List.of()).stream())
                .toList();
        List<Entrega> entregasCurso = actividadesCurso.stream()
                .flatMap(actividad -> entregasPorActividad.getOrDefault(actividad.getId(), List.of()).stream())
                .toList();
        List<SesionClase> sesionesCurso = semanasCurso.stream()
                .flatMap(semana -> sesionesPorSemana.getOrDefault(semana.getId(), List.of()).stream())
                .sorted(Comparator.comparing(SesionClase::getFecha))
                .toList();

        int studentsCount = (int) matriculas.stream()
                .filter(matricula -> matricula.getSeccion() != null
                        && matricula.getSeccion().getCurso() != null
                        && Objects.equals(matricula.getSeccion().getCurso().getId(), curso.getId())
                        && matricula.getEstado() != null
                        && "ACTIVO".equalsIgnoreCase(matricula.getEstado().name()))
                .map(matricula -> matricula.getEstudiante().getId())
                .distinct()
                .count();

        double average = entregasCurso.stream()
                .map(Entrega::getNota)
                .filter(Objects::nonNull)
                .map(BigDecimal::doubleValue)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        long reviewed = entregasCurso.stream()
                .filter(entrega -> entrega.getEstado() == EntregaEstado.REVISADO)
                .count();

        int progress = entregasCurso.isEmpty()
                ? 0
                : (int) Math.round((reviewed * 100.0) / entregasCurso.size());

        String nextClass = sesionesCurso.stream()
                .sorted(Comparator.comparing(SesionClase::getFecha))
                .map(sesion -> sesion.getFecha().format(DateTimeFormatter.ofPattern("dd/MM")))
                .findFirst()
                .orElse("Sin clase programada");

        String status = progress >= 50 ? "activo" : "progreso";

        return new DashboardDocenteResponseDTO.CourseDTO(
                "curso-" + curso.getId(),
                curso.getNombre(),
                curso.getCodigo(),
                seccionesCurso.stream().findFirst().map(Seccion::getNombre).orElse("General"),
                studentsCount,
                formatAverage(average),
                progress,
                nextClass,
                status
        );
    }

    private List<DashboardDocenteResponseDTO.AlertDTO> buildAlerts(
            List<Curso> cursos,
            List<Matricula> matriculas,
            List<Actividad> actividades,
            List<Entrega> entregas
    ) {
        Map<Long, Long> studentsAtRisk = matriculas.stream()
                .filter(matricula -> matricula.getEstudiante() != null
                        && matricula.getEstudiante().getEstadoAcademico() != null
                        && (matricula.getEstudiante().getEstadoAcademico() == EstadoAcademico.OBSERVADO
                        || matricula.getEstudiante().getEstadoAcademico() == EstadoAcademico.CONDICIONADO))
                .collect(Collectors.groupingBy(
                        matricula -> matricula.getSeccion().getCurso().getId(),
                        Collectors.counting()
                ));

        Map<Long, Long> overdueTasks = actividades.stream()
                .filter(actividad -> actividad.getFechaLimite() != null && actividad.getFechaLimite().isBefore(LocalDateTime.now()))
                .collect(Collectors.groupingBy(
                        actividad -> actividad.getSemanaAcademica().getSeccion().getCurso().getId(),
                        Collectors.counting()
                ));

        List<DashboardDocenteResponseDTO.AlertDTO> alerts = new java.util.ArrayList<>();

        cursos.forEach(curso -> {
            long riskyStudents = studentsAtRisk.getOrDefault(curso.getId(), 0L);
            if (riskyStudents > 0) {
                alerts.add(new DashboardDocenteResponseDTO.AlertDTO(
                        "alert-risk-" + curso.getId(),
                        "risk",
                        "Estudiantes en Riesgo (" + riskyStudents + ")",
                        "Hay estudiantes con estado académico observado o condicionado en " + curso.getNombre() + ".",
                        "Ver detalles"
                ));
            }
        });

        cursos.forEach(curso -> {
            long overdue = overdueTasks.getOrDefault(curso.getId(), 0L);
            if (overdue > 0) {
                alerts.add(new DashboardDocenteResponseDTO.AlertDTO(
                        "alert-overdue-" + curso.getId(),
                        "overdue",
                        "Tareas Vencidas (" + overdue + ")",
                        "Existen actividades vencidas sin revisión completa en " + curso.getNombre() + ".",
                        "Enviar recordatorio"
                ));
            }
        });

        if (alerts.isEmpty()) {
            alerts.add(new DashboardDocenteResponseDTO.AlertDTO(
                    "alert-default-1",
                    "risk",
                    "Sin alertas críticas",
                    "Los cursos mostrados no tienen incidencias académicas destacadas por ahora.",
                    "Revisar panel"
            ));
        }

        return alerts;
    }

    private List<DashboardDocenteResponseDTO.ScheduleDTO> buildSchedule(
            List<SesionClase> sesiones,
            Map<Long, List<SemanaAcademica>> semanasPorSeccion
    ) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd/MM");

        return sesiones.stream()
                .sorted(Comparator.comparing(SesionClase::getFecha))
                .map(sesion -> {
                    SemanaAcademica semana = sesion.getSemanaAcademica();
                    Seccion seccion = semana.getSeccion();
                    Curso curso = seccion.getCurso();
                    LocalDate sessionDate = sesion.getFecha();
                    String type = sesion.getTema() != null && sesion.getTema().toLowerCase().contains("evalu")
                            ? "Evaluación"
                            : "Clase";

                    return new DashboardDocenteResponseDTO.ScheduleDTO(
                            "sesion-" + sesion.getId(),
                            sessionDate.isEqual(today) ? "Hoy" : sessionDate.format(timeFormatter),
                            curso.getNombre(),
                            curso.getCodigo() + " · " + seccion.getNombre(),
                            type
                    );
                })
                .limit(2)
                .toList();
    }

    private String getStudentName(Entrega entrega) {
        return entrega.getMatricula().getEstudiante().getUsuario().getNombres()
                + " "
                + entrega.getMatricula().getEstudiante().getUsuario().getApellidos();
    }

    private String getCourseName(Entrega entrega) {
        return entrega.getMatricula().getSeccion().getCurso().getNombre();
    }

    private String getTaskName(Entrega entrega) {
        return entrega.getActividad().getTitulo();
    }

    private String formatRelativeTime(LocalDateTime timestamp) {
        if (timestamp == null) {
            return "Sin fecha";
        }

        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(timestamp, now);
        long days = duration.toDays();
        long hours = duration.toHours();

        if (days <= 0 && hours <= 0) {
            return timestamp.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        }

        if (days == 1) {
            return "Ayer, " + timestamp.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        }

        if (days > 1 && days < 7) {
            return "Hace " + days + " d";
        }

        return timestamp.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM"));
    }

    private String formatAverage(double value) {
        NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("es", "PE"));
        formatter.setMaximumFractionDigits(1);
        formatter.setMinimumFractionDigits(1);
        return formatter.format(BigDecimal.valueOf(value).setScale(1, RoundingMode.HALF_UP));
    }

    private double parseDouble(String value) {
        if (value == null || value.isBlank()) {
            return 0.0;
        }

        try {
            return Double.parseDouble(value.replace(",", "."));
        } catch (NumberFormatException ex) {
            return 0.0;
        }
    }
}
