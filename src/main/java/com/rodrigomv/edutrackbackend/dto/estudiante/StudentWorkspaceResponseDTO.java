package com.rodrigomv.edutrackbackend.dto.estudiante;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record StudentWorkspaceResponseDTO(
        ProfileDTO profile,
        SummaryDTO summary,
        List<CourseDTO> courses,
        List<ActivityDTO> activities,
        List<AlertDTO> alerts,
        List<TimelineItemDTO> timeline
) {

    public record ProfileDTO(
            Long estudianteId,
            Long userId,
            String fullName,
            String email,
            String code,
            String academicStatus
    ) {
    }

    public record SummaryDTO(
            int activeCourses,
            int pendingActivities,
            int deliveredActivities,
            int gradedActivities,
            BigDecimal averageGrade,
            Integer attendancePercent,
            int alertsCount
    ) {
    }

    public record CourseDTO(
            Long matriculaId,
            Long courseId,
            Long sectionId,
            String code,
            String name,
            String description,
            Integer credits,
            String sectionName,
            String period,
            String teacherName,
            int activitiesCount,
            int pendingActivities,
            BigDecimal averageGrade,
            Integer attendancePercent,
            int progress
    ) {
    }

    public record ActivityDTO(
            Long id,
            Long matriculaId,
            Long courseId,
            Long sectionId,
            String courseCode,
            String courseName,
            String sectionName,
            String title,
            String description,
            String type,
            LocalDateTime dueDate,
            BigDecimal maxGrade,
            String status,
            DeliveryDTO delivery
    ) {
    }

    public record DeliveryDTO(
            Long id,
            String status,
            BigDecimal grade,
            LocalDateTime submittedAt,
            String studentComment,
            String teacherComment,
            String fileUrl
    ) {
    }

    public record AlertDTO(
            Long id,
            Long matriculaId,
            String type,
            String description,
            LocalDateTime date,
            String courseName
    ) {
    }

    public record TimelineItemDTO(
            String id,
            String type,
            String title,
            String description,
            LocalDateTime date,
            String courseName,
            BigDecimal grade,
            String status
    ) {
    }
}
