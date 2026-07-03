package com.rodrigomv.edutrackbackend.dto.admin;

import com.rodrigomv.edutrackbackend.persistence.enums.EstadoAcademico;
import com.rodrigomv.edutrackbackend.persistence.enums.PeriodoEstado;
import com.rodrigomv.edutrackbackend.persistence.enums.UsuarioEstado;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record AdminOverviewResponseDTO(
        Summary summary,
        List<UserItem> users,
        List<TeacherItem> teachers,
        List<StudentItem> students,
        List<CourseItem> courses,
        List<SectionItem> sections,
        List<PeriodItem> periods,
        List<RoleItem> roles
) {
    public record Summary(
            long totalUsers,
            long totalTeachers,
            long totalStudents,
            long totalCourses,
            long totalSections,
            long totalEnrollments,
            String activePeriodName
    ) {
    }

    public record UserItem(
            Long id,
            String fullName,
            String email,
            UsuarioEstado estado,
            LocalDateTime createdAt,
            List<String> roles,
            Long docenteId,
            Long estudianteId
    ) {
    }

    public record TeacherItem(
            Long id,
            String fullName,
            String email,
            String codigoDocente,
            String especialidad,
            UsuarioEstado estado,
            int assignedSections
    ) {
    }

    public record StudentItem(
            Long id,
            String fullName,
            String email,
            String codigoEstudiante,
            UsuarioEstado estado,
            EstadoAcademico estadoAcademico,
            int enrollmentCount
    ) {
    }

    public record CourseItem(
            Long id,
            String codigo,
            String nombre,
            String descripcion,
            Integer creditos,
            int seccionesCount,
            int totalMatriculas,
            List<String> docentes
    ) {
    }

    public record SectionItem(
            Long id,
            String nombre,
            Long cursoId,
            String cursoNombre,
            String cursoCodigo,
            Long periodoId,
            String periodoNombre,
            int capacidad,
            int enrolledCount,
            List<String> docentes
    ) {
    }

    public record PeriodItem(
            Long id,
            String nombre,
            LocalDate fechaInicio,
            LocalDate fechaFin,
            PeriodoEstado estado
    ) {
    }

    public record RoleItem(
            Long id,
            String nombre
    ) {
    }
}
