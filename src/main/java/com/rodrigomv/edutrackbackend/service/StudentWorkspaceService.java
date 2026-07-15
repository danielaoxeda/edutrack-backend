package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.dto.estudiante.StudentWorkspaceResponseDTO;
import com.rodrigomv.edutrackbackend.dto.estudiante.StudentActivitySubmissionRequestDTO;
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
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class StudentWorkspaceService {

    private final CurrentStudentService currentStudentService;
    private final MatriculaRepository matriculaRepository;
    private final ActividadRepository actividadRepository;
    private final EntregaRepository entregaRepository;
    private final AsistenciaRepository asistenciaRepository;
    private final AlertaAcademicaRepository alertaAcademicaRepository;

    @Transactional(readOnly = true)
    public StudentWorkspaceResponseDTO getWorkspace() {
        Estudiante estudiante = currentStudentService.getRequiredStudent();
        Usuario usuario = estudiante.getUsuario();

        List<Matricula> matriculas = matriculaRepository.findByEstudianteId(estudiante.getId()).stream()
                .filter(matricula -> matricula.getEstado() == MatriculaEstado.ACTIVO)
                .toList();

        List<Long> matriculaIds = matriculas.stream().map(Matricula::getId).toList();
        List<Long> sectionIds = matriculas.stream()
                .map(Matricula::getSeccion)
                .filter(Objects::nonNull)
                .map(Seccion::getId)
                .toList();

        List<Actividad> activities = sectionIds.isEmpty()
                ? List.of()
                : actividadRepository.findBySemanaAcademicaSeccionIdInAndVisibleTrueOrderByFechaLimiteAsc(sectionIds);
        List<Entrega> deliveries = matriculaIds.isEmpty() ? List.of() : entregaRepository.findByMatriculaIdIn(matriculaIds);
        List<Asistencia> attendance = matriculaIds.isEmpty() ? List.of() : asistenciaRepository.findByMatriculaIdIn(matriculaIds);
        List<AlertaAcademica> alerts = matriculaIds.isEmpty() ? List.of() : alertaAcademicaRepository.findByMatriculaIdIn(matriculaIds);

        Map<Long, Entrega> deliveryByActivityId = deliveries.stream()
                .filter(delivery -> delivery.getActividad() != null)
                .collect(Collectors.toMap(
                        delivery -> delivery.getActividad().getId(),
                        Function.identity(),
                        (first, second) -> second
                ));

        Map<Long, Matricula> enrollmentBySectionId = matriculas.stream()
                .filter(matricula -> matricula.getSeccion() != null)
                .collect(Collectors.toMap(matricula -> matricula.getSeccion().getId(), Function.identity()));

        List<StudentWorkspaceResponseDTO.CourseDTO> courses = matriculas.stream()
                .map(matricula -> buildCourse(matricula, activities, deliveries, attendance, deliveryByActivityId))
                .toList();

        List<StudentWorkspaceResponseDTO.ActivityDTO> activityItems = activities.stream()
                .map(activity -> buildActivity(activity, enrollmentBySectionId, deliveryByActivityId))
                .filter(Objects::nonNull)
                .toList();

        List<StudentWorkspaceResponseDTO.AlertDTO> alertItems = alerts.stream()
                .sorted(Comparator.comparing(AlertaAcademica::getFechaAlerta).reversed())
                .map(this::buildAlert)
                .toList();

        return new StudentWorkspaceResponseDTO(
                new StudentWorkspaceResponseDTO.ProfileDTO(
                        estudiante.getId(),
                        usuario != null ? usuario.getId() : null,
                        usuario != null ? buildFullName(usuario.getNombres(), usuario.getApellidos()) : estudiante.getCodigoEstudiante(),
                        usuario != null ? usuario.getEmail() : null,
                        estudiante.getCodigoEstudiante(),
                        estudiante.getEstadoAcademico() != null ? estudiante.getEstadoAcademico().name() : null
                ),
                buildSummary(courses, activityItems, alertItems),
                courses,
                activityItems,
                alertItems,
                buildTimeline(deliveries, alerts)
        );
    }

    public StudentWorkspaceResponseDTO.DeliveryDTO submitActivity(Long activityId, StudentActivitySubmissionRequestDTO request) {
        Estudiante estudiante = currentStudentService.getRequiredStudent();
        Actividad activity = actividadRepository.findById(activityId)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND,
                        "Actividad no encontrada"
                ));

        if (!Boolean.TRUE.equals(activity.getVisible())) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.FORBIDDEN,
                    "La actividad no esta disponible para entrega"
            );
        }

        Long sectionId = activity.getSemanaAcademica().getSeccion().getId();
        Matricula enrollment = matriculaRepository.findByEstudianteIdAndSeccionId(estudiante.getId(), sectionId).stream()
                .filter(matricula -> matricula.getEstado() == MatriculaEstado.ACTIVO)
                .findFirst()
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.FORBIDDEN,
                        "No estas matriculado en la seccion de esta actividad"
                ));

        String comment = request.comentarioAlumno() != null ? request.comentarioAlumno().trim() : "";
        String fileUrl = request.archivoUrl() != null ? request.archivoUrl().trim() : "";

        if (comment.isBlank() && fileUrl.isBlank()) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.BAD_REQUEST,
                    "Agrega un comentario o un enlace de archivo para enviar la actividad"
            );
        }

        Entrega delivery = entregaRepository.findByActividadIdAndMatriculaId(activity.getId(), enrollment.getId())
                .orElseGet(() -> {
                    Entrega newDelivery = new Entrega();
                    newDelivery.setActividad(activity);
                    newDelivery.setMatricula(enrollment);
                    return newDelivery;
                });

        if (delivery.getNota() != null || delivery.getEstado() == EntregaEstado.CALIFICADO) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.CONFLICT,
                    "La entrega ya fue calificada y no puede modificarse"
            );
        }

        delivery.setComentarioAlumno(comment.isBlank() ? null : comment);
        delivery.setArchivoUrl(fileUrl.isBlank() ? null : fileUrl);
        delivery.setFechaEntrega(LocalDateTime.now());
        delivery.setEstado(activity.getFechaLimite().isBefore(LocalDateTime.now()) ? EntregaEstado.ATRASADO : EntregaEstado.ENTREGADO);

        return buildDelivery(entregaRepository.save(delivery));
    }

    private StudentWorkspaceResponseDTO.CourseDTO buildCourse(
            Matricula matricula,
            List<Actividad> activities,
            List<Entrega> deliveries,
            List<Asistencia> attendance,
            Map<Long, Entrega> deliveryByActivityId
    ) {
        Seccion section = matricula.getSeccion();
        Curso course = section.getCurso();

        List<Actividad> courseActivities = activities.stream()
                .filter(activity -> sameSection(activity, section.getId()))
                .toList();

        int pending = (int) courseActivities.stream()
                .filter(activity -> !deliveryByActivityId.containsKey(activity.getId()))
                .count();

        BigDecimal average = averageGrade(deliveries.stream()
                .filter(delivery -> Objects.equals(delivery.getMatricula().getId(), matricula.getId()))
                .toList());

        Integer attendancePercent = attendancePercent(attendance.stream()
                .filter(row -> Objects.equals(row.getMatricula().getId(), matricula.getId()))
                .toList());

        long completed = courseActivities.stream()
                .filter(activity -> deliveryByActivityId.containsKey(activity.getId()))
                .count();
        int progress = courseActivities.isEmpty() ? 0 : (int) Math.round((completed * 100.0) / courseActivities.size());

        return new StudentWorkspaceResponseDTO.CourseDTO(
                matricula.getId(),
                course.getId(),
                section.getId(),
                course.getCodigo(),
                course.getNombre(),
                course.getDescripcion(),
                course.getCreditos(),
                section.getNombre(),
                section.getPeriodoAcademico() != null ? section.getPeriodoAcademico().getNombre() : null,
                teacherName(section),
                courseActivities.size(),
                pending,
                average,
                attendancePercent,
                progress
        );
    }

    private StudentWorkspaceResponseDTO.ActivityDTO buildActivity(
            Actividad activity,
            Map<Long, Matricula> enrollmentBySectionId,
            Map<Long, Entrega> deliveryByActivityId
    ) {
        Seccion section = activity.getSemanaAcademica().getSeccion();
        Matricula matricula = enrollmentBySectionId.get(section.getId());
        if (matricula == null) {
            return null;
        }

        Curso course = section.getCurso();
        Entrega delivery = deliveryByActivityId.get(activity.getId());

        return new StudentWorkspaceResponseDTO.ActivityDTO(
                activity.getId(),
                matricula.getId(),
                course.getId(),
                section.getId(),
                course.getCodigo(),
                course.getNombre(),
                section.getNombre(),
                activity.getTitulo(),
                activity.getDescripcion(),
                activity.getTipo() != null ? activity.getTipo().name() : null,
                activity.getFechaLimite(),
                activity.getNotaMaxima(),
                activityStatus(activity, delivery),
                buildDelivery(delivery)
        );
    }

    private StudentWorkspaceResponseDTO.DeliveryDTO buildDelivery(Entrega delivery) {
        if (delivery == null) {
            return null;
        }

        return new StudentWorkspaceResponseDTO.DeliveryDTO(
                delivery.getId(),
                delivery.getEstado() != null ? delivery.getEstado().name() : null,
                delivery.getNota(),
                delivery.getFechaEntrega(),
                delivery.getComentarioAlumno(),
                delivery.getComentarioDocente(),
                delivery.getArchivoUrl()
        );
    }

    private StudentWorkspaceResponseDTO.AlertDTO buildAlert(AlertaAcademica alert) {
        Matricula matricula = alert.getMatricula();
        String courseName = matricula != null && matricula.getSeccion() != null
                ? matricula.getSeccion().getCurso().getNombre()
                : null;

        return new StudentWorkspaceResponseDTO.AlertDTO(
                alert.getId(),
                matricula != null ? matricula.getId() : null,
                alert.getTipo() != null ? alert.getTipo().name() : null,
                alert.getDescripcion(),
                alert.getFechaAlerta(),
                courseName
        );
    }

    private StudentWorkspaceResponseDTO.SummaryDTO buildSummary(
            List<StudentWorkspaceResponseDTO.CourseDTO> courses,
            List<StudentWorkspaceResponseDTO.ActivityDTO> activities,
            List<StudentWorkspaceResponseDTO.AlertDTO> alerts
    ) {
        int pending = (int) activities.stream()
                .filter(activity -> "PENDIENTE".equals(activity.status()) || "VENCIDA".equals(activity.status()))
                .count();
        int delivered = (int) activities.stream()
                .filter(activity -> activity.delivery() != null)
                .count();
        int graded = (int) activities.stream()
                .filter(activity -> activity.delivery() != null && activity.delivery().grade() != null)
                .count();

        BigDecimal average = average(courses.stream()
                .map(StudentWorkspaceResponseDTO.CourseDTO::averageGrade)
                .filter(Objects::nonNull)
                .toList());

        Integer attendance = averageInteger(courses.stream()
                .map(StudentWorkspaceResponseDTO.CourseDTO::attendancePercent)
                .filter(Objects::nonNull)
                .toList());

        return new StudentWorkspaceResponseDTO.SummaryDTO(
                courses.size(),
                pending,
                delivered,
                graded,
                average,
                attendance,
                alerts.size()
        );
    }

    private List<StudentWorkspaceResponseDTO.TimelineItemDTO> buildTimeline(List<Entrega> deliveries, List<AlertaAcademica> alerts) {
        Stream<StudentWorkspaceResponseDTO.TimelineItemDTO> deliveryItems = deliveries.stream()
                .map(delivery -> new StudentWorkspaceResponseDTO.TimelineItemDTO(
                        "delivery-" + delivery.getId(),
                        "ENTREGA",
                        delivery.getActividad().getTitulo(),
                        delivery.getComentarioDocente(),
                        delivery.getFechaEntrega(),
                        delivery.getActividad().getSemanaAcademica().getSeccion().getCurso().getNombre(),
                        delivery.getNota(),
                        delivery.getEstado() != null ? delivery.getEstado().name() : null
                ));

        Stream<StudentWorkspaceResponseDTO.TimelineItemDTO> alertItems = alerts.stream()
                .map(alert -> new StudentWorkspaceResponseDTO.TimelineItemDTO(
                        "alert-" + alert.getId(),
                        "ALERTA",
                        alert.getTipo() != null ? alert.getTipo().name() : "Alerta academica",
                        alert.getDescripcion(),
                        alert.getFechaAlerta(),
                        alert.getMatricula().getSeccion().getCurso().getNombre(),
                        null,
                        null
                ));

        return Stream.concat(deliveryItems, alertItems)
                .sorted(Comparator.comparing(StudentWorkspaceResponseDTO.TimelineItemDTO::date).reversed())
                .toList();
    }

    private String activityStatus(Actividad activity, Entrega delivery) {
        if (delivery != null && delivery.getNota() != null) {
            return "CALIFICADA";
        }

        if (delivery != null && delivery.getEstado() != null) {
            return delivery.getEstado().name();
        }

        return activity.getFechaLimite().isBefore(LocalDateTime.now()) ? "VENCIDA" : "PENDIENTE";
    }

    private boolean sameSection(Actividad activity, Long sectionId) {
        return Optional.ofNullable(activity.getSemanaAcademica())
                .map(SemanaAcademica::getSeccion)
                .map(Seccion::getId)
                .filter(id -> Objects.equals(id, sectionId))
                .isPresent();
    }

    private String teacherName(Seccion section) {
        return section.getDocenteSecciones().stream()
                .map(DocenteSeccion::getDocente)
                .filter(Objects::nonNull)
                .map(Docente::getUsuario)
                .filter(Objects::nonNull)
                .map(user -> buildFullName(user.getNombres(), user.getApellidos()))
                .findFirst()
                .orElse("Sin docente asignado");
    }

    private BigDecimal averageGrade(List<Entrega> deliveries) {
        return average(deliveries.stream()
                .filter(delivery -> delivery.getEstado() == EntregaEstado.CALIFICADO || delivery.getNota() != null)
                .map(Entrega::getNota)
                .filter(Objects::nonNull)
                .toList());
    }

    private BigDecimal average(List<BigDecimal> values) {
        if (values.isEmpty()) {
            return null;
        }

        BigDecimal sum = values.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.divide(BigDecimal.valueOf(values.size()), 2, RoundingMode.HALF_UP);
    }

    private Integer attendancePercent(List<Asistencia> rows) {
        if (rows.isEmpty()) {
            return null;
        }

        long attended = rows.stream()
                .filter(row -> row.getEstado() == AsistenciaEstado.PRESENTE || row.getEstado() == AsistenciaEstado.TARDE)
                .count();

        return (int) Math.round((attended * 100.0) / rows.size());
    }

    private Integer averageInteger(List<Integer> values) {
        if (values.isEmpty()) {
            return null;
        }

        return (int) Math.round(values.stream().mapToInt(Integer::intValue).average().orElse(0));
    }

    private String buildFullName(String firstName, String lastName) {
        return Stream.of(firstName, lastName)
                .filter(value -> value != null && !value.isBlank())
                .collect(Collectors.joining(" "))
                .trim();
    }
}
