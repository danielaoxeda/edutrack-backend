package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.dto.docente.TeacherWorkspaceResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.entity.*;
import com.rodrigomv.edutrackbackend.persistence.enums.AsistenciaEstado;
import com.rodrigomv.edutrackbackend.persistence.enums.EntregaEstado;
import com.rodrigomv.edutrackbackend.persistence.enums.MatriculaEstado;
import com.rodrigomv.edutrackbackend.persistence.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherWorkspaceService {

    private final CursoRepository cursoRepository;
    private final SeccionRepository seccionRepository;
    private final MatriculaRepository matriculaRepository;
    private final ActividadRepository actividadRepository;
    private final EntregaRepository entregaRepository;
    private final AsistenciaRepository asistenciaRepository;
    private final SemanaAcademicaRepository semanaAcademicaRepository;
    private final SesionClaseRepository sesionClaseRepository;

    @Transactional(readOnly = true)
    public TeacherWorkspaceResponseDTO getWorkspace() {
        List<Curso> cursos = cursoRepository.findAll();
        List<Seccion> secciones = seccionRepository.findAll();
        List<Matricula> matriculas = matriculaRepository.findAll();
        List<Actividad> actividades = actividadRepository.findAll();
        List<Entrega> entregas = entregaRepository.findAll();
        List<Asistencia> asistencias = asistenciaRepository.findAll();
        List<SemanaAcademica> semanas = semanaAcademicaRepository.findAll();
        List<SesionClase> sesiones = sesionClaseRepository.findAll();

        Map<Long, List<Seccion>> seccionesPorCurso = secciones.stream()
                .collect(Collectors.groupingBy(seccion -> seccion.getCurso().getId()));
        Map<Long, List<SemanaAcademica>> semanasPorSeccion = semanas.stream()
                .collect(Collectors.groupingBy(semana -> semana.getSeccion().getId()));
        Map<Long, List<Actividad>> actividadesPorSemana = actividades.stream()
                .collect(Collectors.groupingBy(actividad -> actividad.getSemanaAcademica().getId()));
        Map<Long, List<Entrega>> entregasPorActividad = entregas.stream()
                .collect(Collectors.groupingBy(entrega -> entrega.getActividad().getId()));
        Map<Long, List<Asistencia>> asistenciasPorMatricula = asistencias.stream()
                .collect(Collectors.groupingBy(asistencia -> asistencia.getMatricula().getId()));
        Map<Long, List<SesionClase>> sesionesPorSemana = sesiones.stream()
                .collect(Collectors.groupingBy(sesion -> sesion.getSemanaAcademica().getId()));

        List<TeacherWorkspaceResponseDTO.StudentDTO> studentRows = buildStudents(matriculas, entregasPorActividad, asistenciasPorMatricula);
        List<TeacherWorkspaceResponseDTO.StudentAlertDTO> studentAlerts = buildStudentAlerts(studentRows, actividades, entregas);

        List<TeacherWorkspaceResponseDTO.TaskDTO> taskRows = buildTasks(cursos, seccionesPorCurso, semanasPorSeccion, actividadesPorSemana, entregasPorActividad, matriculas);
        List<TeacherWorkspaceResponseDTO.RecentSubmissionDTO> recentSubmissions = buildRecentSubmissions(entregas);
        List<TeacherWorkspaceResponseDTO.UrgentTaskDTO> urgentTasks = buildUrgentTasks(taskRows);

        List<TeacherWorkspaceResponseDTO.AttendanceDTO> attendanceRows = buildAttendance(matriculas, asistenciasPorMatricula);

        List<TeacherWorkspaceResponseDTO.GradeDTO> gradeRows = buildGrades(matriculas, entregasPorActividad);
        List<TeacherWorkspaceResponseDTO.GradeDistributionDTO> distribution = buildGradeDistribution(gradeRows);

        return new TeacherWorkspaceResponseDTO(
                new TeacherWorkspaceResponseDTO.StudentsSectionDTO(
                        buildStudentStats(studentRows),
                        studentRows,
                        studentAlerts
                ),
                new TeacherWorkspaceResponseDTO.TasksSectionDTO(
                        buildTaskStats(taskRows, entregas),
                        taskRows,
                        recentSubmissions,
                        urgentTasks
                ),
                new TeacherWorkspaceResponseDTO.AttendanceSectionDTO(
                        buildAttendanceStats(attendanceRows, sesiones.size()),
                        attendanceRows
                ),
                new TeacherWorkspaceResponseDTO.GradesSectionDTO(
                        buildGradeStats(gradeRows, actividades),
                        gradeRows,
                        distribution
                )
        );
    }

    @Transactional
    public void saveAttendance(String date, List<TeacherWorkspaceResponseDTO.AttendanceDTO> rows) {
        LocalDate targetDate = parseDate(date);
        SesionClase session = findBestSession(targetDate);
        if (session == null) {
            return;
        }

        for (TeacherWorkspaceResponseDTO.AttendanceDTO row : rows) {
            Matricula matricula = matriculaRepository.findAll().stream()
                    .filter(m -> m.getEstudiante() != null
                            && m.getEstudiante().getCodigoEstudiante() != null
                            && Objects.equals(m.getEstudiante().getCodigoEstudiante(), row.getCode()))
                    .findFirst()
                    .orElse(null);

            if (matricula == null) {
                continue;
            }

            AsistenciaEstado estado = switch (row.getTodayStatus()) {
                case "tardanza" -> AsistenciaEstado.TARDE;
                case "falta" -> AsistenciaEstado.FALTA;
                default -> AsistenciaEstado.PRESENTE;
            };

            Asistencia asistencia = asistenciaRepository.findBySesionClaseIdAndMatriculaId(session.getId(), matricula.getId())
                    .orElseGet(Asistencia::new);
            asistencia.setSesionClase(session);
            asistencia.setMatricula(matricula);
            asistencia.setEstado(estado);
            asistencia.setJustificada(false);
            asistenciaRepository.save(asistencia);
        }
    }

    private List<TeacherWorkspaceResponseDTO.StatDTO> buildStudentStats(List<TeacherWorkspaceResponseDTO.StudentDTO> students) {
        long total = students.size();
        long active = students.stream().filter(s -> "regular".equalsIgnoreCase(s.getStatus()) || "sobresaliente".equalsIgnoreCase(s.getStatus())).count();
        long risk = students.stream().filter(s -> "riesgo".equalsIgnoreCase(s.getStatus())).count();
        double avgGrade = students.stream().mapToDouble(TeacherWorkspaceResponseDTO.StudentDTO::getAverageGrade).average().orElse(0.0);
        double avgAttendance = students.stream().mapToInt(TeacherWorkspaceResponseDTO.StudentDTO::getAttendance).average().orElse(0.0);

        return List.of(
                new TeacherWorkspaceResponseDTO.StatDTO("Total Estudiantes", total, null, "Users"),
                new TeacherWorkspaceResponseDTO.StatDTO("Activos", active, "en seguimiento", "CheckCircle2"),
                new TeacherWorkspaceResponseDTO.StatDTO("En Riesgo", risk, "requieren atención", "AlertTriangle"),
                new TeacherWorkspaceResponseDTO.StatDTO("Promedio General", format1(avgGrade), "/ 5.0", "TrendingUp"),
                new TeacherWorkspaceResponseDTO.StatDTO("Asistencia Promedio", formatPercent(avgAttendance), null, "CalendarCheck")
        );
    }

    private List<TeacherWorkspaceResponseDTO.StudentDTO> buildStudents(List<Matricula> matriculas, Map<Long, List<Entrega>> entregasPorActividad, Map<Long, List<Asistencia>> asistenciasPorMatricula) {
        Map<Long, List<Matricula>> matriculasPorEstudiante = matriculas.stream()
                .filter(m -> m.getEstado() == MatriculaEstado.ACTIVO)
                .collect(Collectors.groupingBy(m -> m.getEstudiante().getId()));

        List<TeacherWorkspaceResponseDTO.StudentDTO> students = new ArrayList<>();
        for (Map.Entry<Long, List<Matricula>> entry : matriculasPorEstudiante.entrySet()) {
            Matricula representative = entry.getValue().get(0);
            Estudiante estudiante = representative.getEstudiante();
            Usuario usuario = estudiante.getUsuario();
            List<Matricula> studentMatriculas = entry.getValue();

            List<Entrega> studentEntregas = studentMatriculas.stream()
                    .flatMap(m -> m.getEntregas().stream())
                    .collect(Collectors.toList());

            if (studentEntregas.isEmpty()) {
                studentEntregas = entregasPorActividad.values().stream()
                        .flatMap(List::stream)
                        .filter(e -> e.getMatricula() != null && e.getMatricula().getEstudiante().getId().equals(estudiante.getId()))
                        .collect(Collectors.toList());
            }

            List<Asistencia> studentAsistencias = studentMatriculas.stream()
                    .flatMap(m -> asistenciasPorMatricula.getOrDefault(m.getId(), List.of()).stream())
                    .collect(Collectors.toList());

            double averageGrade = studentEntregas.stream()
                    .map(Entrega::getNota)
                    .filter(Objects::nonNull)
                    .map(BigDecimal::doubleValue)
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(safeAverage(studentMatriculas));

            int attendance = (int) Math.round(studentAsistencias.stream()
                    .mapToInt(a -> a.getEstado() == AsistenciaEstado.PRESENTE ? 100 : a.getEstado() == AsistenciaEstado.TARDE ? 75 : 0)
                    .average()
                    .orElse(92.0));

            String status = averageGrade < 3.0 || attendance < 75 ? "riesgo" : averageGrade >= 4.5 && attendance >= 90 ? "sobresaliente" : "regular";

            students.add(new TeacherWorkspaceResponseDTO.StudentDTO(
                    "stu-" + estudiante.getId(),
                    usuario.getNombres() + " " + usuario.getApellidos(),
                    usuario.getEmail(),
                    estudiante.getCodigoEstudiante(),
                    representative.getSeccion().getCurso().getNombre(),
                    representative.getSeccion().getNombre(),
                    round1(averageGrade),
                    attendance,
                    status
            ));
        }

        return students.stream()
                .sorted(Comparator.comparing(TeacherWorkspaceResponseDTO.StudentDTO::getName))
                .toList();
    }

    private List<TeacherWorkspaceResponseDTO.StudentAlertDTO> buildStudentAlerts(List<TeacherWorkspaceResponseDTO.StudentDTO> students, List<Actividad> actividades, List<Entrega> entregas) {
        List<TeacherWorkspaceResponseDTO.StudentAlertDTO> alerts = new ArrayList<>();

        students.stream()
                .filter(s -> s.getAttendance() < 75)
                .limit(1)
                .forEach(s -> alerts.add(new TeacherWorkspaceResponseDTO.StudentAlertDTO(
                        "sa-att-" + s.getCode(),
                        s.getName(),
                        "Hace 2h",
                        "Ausencia consecutiva detectada en " + s.getCourse() + ".",
                        "Asistencia",
                        s.getAttendance() + "%",
                        "attendance"
                )));

        students.stream()
                .filter(s -> s.getAverageGrade() < 3.0)
                .limit(1)
                .forEach(s -> alerts.add(new TeacherWorkspaceResponseDTO.StudentAlertDTO(
                        "sa-grade-" + s.getCode(),
                        s.getName(),
                        "Ayer",
                        "Calificación crítica en su última evaluación.",
                        "Nota",
                        format1(s.getAverageGrade()) + " / 5.0",
                        "grade"
                )));

        long overdueTasks = actividades.stream().filter(a -> a.getFechaLimite().isBefore(LocalDateTime.now()) && Boolean.FALSE.equals(a.getCalificada())).count();
        if (overdueTasks > 0) {
            alerts.add(new TeacherWorkspaceResponseDTO.StudentAlertDTO(
                    "sa-homework-1",
                    "Grupo activo",
                    "Ayer",
                    "Hay actividades sin entregar o pendientes de revisión.",
                    "Pendientes",
                    overdueTasks + " tareas",
                    "homework"
            ));
        }

        if (alerts.isEmpty()) {
            alerts.add(new TeacherWorkspaceResponseDTO.StudentAlertDTO(
                    "sa-default",
                    "Sin alertas",
                    "Hoy",
                    "Todo el grupo se encuentra dentro de parámetros normales.",
                    "Estado",
                    "OK",
                    "homework"
            ));
        }

        return alerts;
    }

    private List<TeacherWorkspaceResponseDTO.TaskDTO> buildTasks(
            List<Curso> cursos,
            Map<Long, List<Seccion>> seccionesPorCurso,
            Map<Long, List<SemanaAcademica>> semanasPorSeccion,
            Map<Long, List<Actividad>> actividadesPorSemana,
            Map<Long, List<Entrega>> entregasPorActividad,
            List<Matricula> matriculas
    ) {
        List<TeacherWorkspaceResponseDTO.TaskDTO> tasks = new ArrayList<>();

        for (Curso curso : cursos) {
            List<Seccion> seccionesCurso = seccionesPorCurso.getOrDefault(curso.getId(), List.of());
            for (Seccion seccion : seccionesCurso) {
                List<SemanaAcademica> semanas = semanasPorSeccion.getOrDefault(seccion.getId(), List.of());
                for (SemanaAcademica semana : semanas) {
                    List<Actividad> actividades = actividadesPorSemana.getOrDefault(semana.getId(), List.of());
                    for (Actividad actividad : actividades) {
                        List<Entrega> entregasActividad = entregasPorActividad.getOrDefault(actividad.getId(), List.of());
                        int totalCount = (int) matriculas.stream()
                                .filter(m -> m.getSeccion() != null && Objects.equals(m.getSeccion().getId(), seccion.getId()) && m.getEstado() == MatriculaEstado.ACTIVO)
                                .count();
                        int receivedCount = (int) entregasActividad.stream().count();
                        String status = Boolean.TRUE.equals(actividad.getCalificada())
                                ? "evaluado"
                                : actividad.getFechaLimite().isBefore(LocalDateTime.now()) ? "calificando" : "activo";

                        tasks.add(new TeacherWorkspaceResponseDTO.TaskDTO(
                                "task-" + actividad.getId(),
                                actividad.getTitulo(),
                                curso.getNombre(),
                                seccion.getNombre(),
                                actividad.getFechaLimite().minusDays(10).toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM")),
                                actividad.getFechaLimite().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM")),
                                receivedCount,
                                totalCount,
                                status
                        ));
                    }
                }
            }
        }

        return tasks.stream()
                .sorted(Comparator.comparing(TeacherWorkspaceResponseDTO.TaskDTO::getLimitDate).reversed())
                .toList();
    }

    private List<TeacherWorkspaceResponseDTO.RecentSubmissionDTO> buildRecentSubmissions(List<Entrega> entregas) {
        return entregas.stream()
                .sorted(Comparator.comparing(Entrega::getFechaEntrega).reversed())
                .limit(3)
                .map(entrega -> new TeacherWorkspaceResponseDTO.RecentSubmissionDTO(
                        "sub-" + entrega.getId(),
                        fullName(entrega.getMatricula().getEstudiante().getUsuario()),
                        entrega.getActividad().getTitulo(),
                        entrega.getMatricula().getSeccion().getCurso().getNombre(),
                        relativeTime(entrega.getFechaEntrega())
                ))
                .toList();
    }

    private List<TeacherWorkspaceResponseDTO.UrgentTaskDTO> buildUrgentTasks(List<TeacherWorkspaceResponseDTO.TaskDTO> tasks) {
        return tasks.stream()
                .filter(task -> task.getStatus().equals("activo") || task.getStatus().equals("calificando"))
                .sorted(Comparator.comparing(TeacherWorkspaceResponseDTO.TaskDTO::getLimitDate))
                .limit(2)
                .map(task -> new TeacherWorkspaceResponseDTO.UrgentTaskDTO(
                        "urg-" + task.getId(),
                        task.getName(),
                        task.getCourse(),
                        task.getLimitDate().equals(DateTimeFormatter.ofPattern("dd/MM").format(LocalDate.now())) ? "Vence hoy" : "Vence pronto",
                        task.getReceivedCount(),
                        task.getTotalCount(),
                        task.getTotalCount() == 0 ? 0 : (int) Math.round((task.getReceivedCount() * 100.0) / task.getTotalCount())
                ))
                .toList();
    }

    private List<TeacherWorkspaceResponseDTO.StatDTO> buildTaskStats(List<TeacherWorkspaceResponseDTO.TaskDTO> tasks, List<Entrega> entregas) {
        long active = tasks.stream().filter(t -> "activo".equalsIgnoreCase(t.getStatus())).count();
        long pendingReview = entregas.stream().filter(e -> e.getEstado() != EntregaEstado.REVISADO).count();
        long delivered = entregas.size();
        long overdue = tasks.stream().filter(t -> "calificando".equalsIgnoreCase(t.getStatus())).count();
        double compliance = tasks.stream().mapToInt(t -> t.getTotalCount() == 0 ? 0 : (int) Math.round((t.getReceivedCount() * 100.0) / t.getTotalCount())).average().orElse(0.0);

        return List.of(
                new TeacherWorkspaceResponseDTO.StatDTO("Tareas activas", active, "vigentes", "FileText"),
                new TeacherWorkspaceResponseDTO.StatDTO("Pendientes revisión", pendingReview, "por calificar", "ClipboardList"),
                new TeacherWorkspaceResponseDTO.StatDTO("Entregas recibidas", delivered, "enviadas", "Inbox"),
                new TeacherWorkspaceResponseDTO.StatDTO("Tareas vencidas", overdue, "requiere atención", "AlertTriangle"),
                new TeacherWorkspaceResponseDTO.StatDTO("Promedio cumplimiento", formatPercent(compliance), null, "TrendingUp")
        );
    }

    private List<TeacherWorkspaceResponseDTO.AttendanceDTO> buildAttendance(List<Matricula> matriculas, Map<Long, List<Asistencia>> asistenciasPorMatricula) {
        return matriculas.stream()
                .filter(m -> m.getEstado() == MatriculaEstado.ACTIVO)
                .map(m -> {
                    Estudiante e = m.getEstudiante();
                    Usuario u = e.getUsuario();
                    List<Asistencia> asistencias = asistenciasPorMatricula.getOrDefault(m.getId(), List.of());
                    int attendance = (int) Math.round(asistencias.stream()
                            .mapToInt(a -> a.getEstado() == AsistenciaEstado.PRESENTE ? 100 : a.getEstado() == AsistenciaEstado.TARDE ? 75 : 0)
                            .average()
                            .orElse(92.0));
                    String todayStatus = asistencias.stream()
                            .sorted(Comparator.comparing(Asistencia::getId).reversed())
                            .map(a -> switch (a.getEstado()) {
                                case TARDE -> "tardanza";
                                case FALTA -> "falta";
                                default -> "presente";
                            })
                            .findFirst()
                            .orElse("presente");

                    return new TeacherWorkspaceResponseDTO.AttendanceDTO(
                            "att-" + e.getId(),
                            u.getNombres() + " " + u.getApellidos(),
                            u.getEmail(),
                            e.getCodigoEstudiante(),
                            m.getSeccion().getCurso().getNombre(),
                            m.getSeccion().getNombre(),
                            attendance,
                            todayStatus
                    );
                })
                .sorted(Comparator.comparing(TeacherWorkspaceResponseDTO.AttendanceDTO::getName))
                .toList();
    }

    private List<TeacherWorkspaceResponseDTO.StatDTO> buildAttendanceStats(List<TeacherWorkspaceResponseDTO.AttendanceDTO> attendanceRows, int sessionsCount) {
        long total = attendanceRows.size();
        long present = attendanceRows.stream().filter(a -> "presente".equalsIgnoreCase(a.getTodayStatus())).count();
        long late = attendanceRows.stream().filter(a -> "tardanza".equalsIgnoreCase(a.getTodayStatus())).count();
        long absent = attendanceRows.stream().filter(a -> "falta".equalsIgnoreCase(a.getTodayStatus())).count();
        double avg = attendanceRows.stream().mapToInt(TeacherWorkspaceResponseDTO.AttendanceDTO::getAttendance).average().orElse(0.0);

        return List.of(
                new TeacherWorkspaceResponseDTO.StatDTO("Estudiantes", total, null, "Users"),
                new TeacherWorkspaceResponseDTO.StatDTO("Presentes", present, null, "CheckCircle2"),
                new TeacherWorkspaceResponseDTO.StatDTO("Tardanzas", late, null, "Clock3"),
                new TeacherWorkspaceResponseDTO.StatDTO("Faltas", absent, null, "AlertTriangle"),
                new TeacherWorkspaceResponseDTO.StatDTO("Asistencia Promedio", formatPercent(avg), null, "CalendarCheck"),
                new TeacherWorkspaceResponseDTO.StatDTO("Clases Impartidas", sessionsCount, "sesiones", "Calendar")
        );
    }

    private List<TeacherWorkspaceResponseDTO.GradeDTO> buildGrades(List<Matricula> matriculas, Map<Long, List<Entrega>> entregasPorActividad) {
        List<TeacherWorkspaceResponseDTO.GradeDTO> grades = new ArrayList<>();
        for (Matricula matricula : matriculas.stream().filter(m -> m.getEstado() == MatriculaEstado.ACTIVO).toList()) {
            Estudiante estudiante = matricula.getEstudiante();
            Usuario usuario = estudiante.getUsuario();
            List<Entrega> entregas = matricula.getEntregas().isEmpty()
                    ? entregasPorActividad.values().stream().flatMap(List::stream)
                        .filter(e -> e.getMatricula() != null && e.getMatricula().getId().equals(matricula.getId()))
                        .collect(Collectors.toList())
                    : matricula.getEntregas();

            List<Double> notes = entregas.stream()
                    .map(Entrega::getNota)
                    .filter(Objects::nonNull)
                    .map(BigDecimal::doubleValue)
                    .sorted()
                    .toList();

            double pc1 = noteAt(notes, 0);
            double pc2 = noteAt(notes, 1);
            double parcial = noteAt(notes, 2);
            double finalGrade = noteAt(notes, 3);
            double average = List.of(pc1, pc2, parcial, finalGrade).stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
            String status = average >= 3.0 ? "aprobado" : "reprobado";
            if (notes.isEmpty()) {
                status = "pendiente";
            }

            grades.add(new TeacherWorkspaceResponseDTO.GradeDTO(
                    "gr-" + estudiante.getId(),
                    usuario.getNombres() + " " + usuario.getApellidos(),
                    estudiante.getCodigoEstudiante(),
                    matricula.getSeccion().getCurso().getNombre(),
                    matricula.getSeccion().getNombre(),
                    round1(pc1),
                    round1(pc2),
                    round1(parcial),
                    round1(finalGrade),
                    round1(average),
                    status
            ));
        }

        return grades.stream().sorted(Comparator.comparing(TeacherWorkspaceResponseDTO.GradeDTO::getName)).toList();
    }

    private List<TeacherWorkspaceResponseDTO.StatDTO> buildGradeStats(List<TeacherWorkspaceResponseDTO.GradeDTO> grades, List<Actividad> actividades) {
        double average = grades.stream().mapToDouble(TeacherWorkspaceResponseDTO.GradeDTO::getAverage).average().orElse(0.0);
        long approved = grades.stream().filter(g -> "aprobado".equalsIgnoreCase(g.getStatus())).count();
        long pending = grades.stream().filter(g -> "pendiente".equalsIgnoreCase(g.getStatus())).count();
        long exams = actividades.stream().filter(a -> a.getTipo() != null && a.getTipo().name().equals("EXAMEN")).count();
        long evaluated = grades.stream().filter(g -> g.getAverage() > 0).count();

        return List.of(
                new TeacherWorkspaceResponseDTO.StatDTO("Promedio General", format1(average), null, "TrendingUp"),
                new TeacherWorkspaceResponseDTO.StatDTO("Tasa de Aprobación", formatPercent(grades.isEmpty() ? 0 : (approved * 100.0 / grades.size())), null, "CheckCircle2"),
                new TeacherWorkspaceResponseDTO.StatDTO("Notas Pendientes", pending, "por registrar", "ClipboardCopy"),
                new TeacherWorkspaceResponseDTO.StatDTO("Exámenes Realizados", exams, "tomados", "BookOpen"),
                new TeacherWorkspaceResponseDTO.StatDTO("Estudiantes Evaluados", evaluated, "del total", "Users")
        );
    }

    private List<TeacherWorkspaceResponseDTO.GradeDistributionDTO> buildGradeDistribution(List<TeacherWorkspaceResponseDTO.GradeDTO> grades) {
        long high = grades.stream().filter(g -> g.getAverage() >= 4.5).count();
        long mid = grades.stream().filter(g -> g.getAverage() >= 3.0 && g.getAverage() < 4.5).count();
        long low = grades.stream().filter(g -> g.getAverage() < 3.0).count();
        long total = Math.max(grades.size(), 1);

        return List.of(
                new TeacherWorkspaceResponseDTO.GradeDistributionDTO(
                        "Sobresaliente (4.5 - 5.0)",
                        (int) high,
                        (int) Math.round((high * 100.0) / total),
                        "from-emerald-400 to-emerald-600",
                        "bg-emerald-50 text-emerald-700 border-emerald-100"
                ),
                new TeacherWorkspaceResponseDTO.GradeDistributionDTO(
                        "Regular / Aprobado (3.0 - 4.4)",
                        (int) mid,
                        (int) Math.round((mid * 100.0) / total),
                        "from-blue-400 to-blue-600",
                        "bg-blue-50 text-blue-700 border-blue-100"
                ),
                new TeacherWorkspaceResponseDTO.GradeDistributionDTO(
                        "En Riesgo / Reprobado (0.0 - 2.9)",
                        (int) low,
                        (int) Math.round((low * 100.0) / total),
                        "from-rose-400 to-rose-600",
                        "bg-rose-50 text-rose-700 border-rose-100"
                )
        );
    }

    private double safeAverage(List<Matricula> matriculas) {
        return matriculas.stream()
                .map(m -> m.getEstudiante().getEstadoAcademico())
                .filter(Objects::nonNull)
                .mapToInt(estado -> estado == null ? 3 : estado.ordinal() + 2)
                .average()
                .orElse(3.2);
    }

    private double noteAt(List<Double> notes, int index) {
        if (notes.isEmpty()) {
            return 0.0;
        }
        if (index < notes.size()) {
            return notes.get(index);
        }
        return notes.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    private double round1(double value) {
        return BigDecimal.valueOf(value).setScale(1, RoundingMode.HALF_UP).doubleValue();
    }

    private String format1(double value) {
        return String.format(new Locale("es", "PE"), "%.1f", value);
    }

    private String formatPercent(double value) {
        return Math.round(value) + "%";
    }

    private String relativeTime(LocalDateTime timestamp) {
        Duration duration = Duration.between(timestamp, LocalDateTime.now());
        long days = duration.toDays();
        long hours = duration.toHours();
        if (days <= 0 && hours <= 0) {
            return timestamp.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        }
        if (days == 1) {
            return "Ayer, " + timestamp.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        }
        if (days < 7) {
            return "Hace " + days + " d";
        }
        return timestamp.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM"));
    }

    private String fullName(Usuario usuario) {
        return usuario.getNombres() + " " + usuario.getApellidos();
    }

    private LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (Exception ex) {
            return LocalDate.now();
        }
    }

    private SesionClase findBestSession(LocalDate date) {
        List<SesionClase> exact = sesionClaseRepository.findByFecha(date);
        if (!exact.isEmpty()) {
            return exact.get(0);
        }
        return sesionClaseRepository.findAll().stream()
                .max(Comparator.comparing(SesionClase::getFecha))
                .orElse(null);
    }
}
