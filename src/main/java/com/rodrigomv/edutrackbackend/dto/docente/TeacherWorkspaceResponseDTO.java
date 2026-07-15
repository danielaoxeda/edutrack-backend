package com.rodrigomv.edutrackbackend.dto.docente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherWorkspaceResponseDTO {

    private StudentsSectionDTO students;
    private TasksSectionDTO tasks;
    private AttendanceSectionDTO attendance;
    private GradesSectionDTO grades;

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
    public static class StudentsSectionDTO {
        private List<StatDTO> stats;
        private List<StudentDTO> students;
        private List<StudentAlertDTO> alerts;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StudentDTO {
        private String id;
        private String name;
        private String email;
        private String code;
        private String course;
        private String group;
        private Double averageGrade;
        private Integer attendance;
        private String status;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StudentAlertDTO {
        private String id;
        private String studentName;
        private String timeText;
        private String description;
        private String metaLabel;
        private String metaValue;
        private String type;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TasksSectionDTO {
        private List<StatDTO> stats;
        private List<TaskDTO> tasks;
        private List<RecentSubmissionDTO> recentSubmissions;
        private List<UrgentTaskDTO> urgentTasks;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TaskDTO {
        private String id;
        private String name;
        private String course;
        private String group;
        private String publishedDate;
        private String limitDate;
        private int receivedCount;
        private int totalCount;
        private String status;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecentSubmissionDTO {
        private String id;
        private Long deliveryId;
        private String studentName;
        private String taskName;
        private String courseName;
        private String timeAgo;
        private String status;
        private Double maxGrade;
        private Double grade;
        private String studentComment;
        private String teacherComment;
        private String fileUrl;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UrgentTaskDTO {
        private String id;
        private String title;
        private String courseName;
        private String dueText;
        private int receivedCount;
        private int totalCount;
        private int percentage;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AttendanceSectionDTO {
        private List<StatDTO> stats;
        private List<AttendanceDTO> attendanceList;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AttendanceDTO {
        private String id;
        private String name;
        private String email;
        private String code;
        private String course;
        private String group;
        private Integer attendance;
        private String todayStatus;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GradesSectionDTO {
        private List<StatDTO> stats;
        private List<GradeDTO> grades;
        private List<GradeDistributionDTO> distribution;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GradeDTO {
        private String id;
        private String name;
        private String code;
        private String course;
        private String group;
        private double pc1;
        private double pc2;
        private double parcial;
        private double finalGrade;
        private double average;
        private String status;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GradeDistributionDTO {
        private String label;
        private int count;
        private int percent;
        private String color;
        private String bgBadge;
    }
}
