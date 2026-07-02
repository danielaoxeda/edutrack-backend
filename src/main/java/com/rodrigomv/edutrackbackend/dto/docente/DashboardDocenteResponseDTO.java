package com.rodrigomv.edutrackbackend.dto.docente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDocenteResponseDTO {

    private List<StatDTO> stats;
    private List<CourseDTO> courses;
    private List<PendingReviewDTO> pendingReviews;
    private List<AlertDTO> alerts;
    private List<ScheduleDTO> schedule;
    private List<CourseAverageDTO> courseAverages;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StatDTO {
        private String label;
        private Object value;
        private String subtext;
        private String iconName;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CourseDTO {
        private String id;
        private String title;
        private String code;
        private String group;
        private Integer studentsCount;
        private String averageGrade;
        private Integer progress;
        private String nextClass;
        private String status;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PendingReviewDTO {
        private String id;
        private String studentName;
        private String courseName;
        private String taskName;
        private String time;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AlertDTO {
        private String id;
        private String type;
        private String title;
        private String description;
        private String actionLabel;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScheduleDTO {
        private String id;
        private String time;
        private String title;
        private String location;
        private String type;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CourseAverageDTO {
        private String course;
        private double average;
    }
}
