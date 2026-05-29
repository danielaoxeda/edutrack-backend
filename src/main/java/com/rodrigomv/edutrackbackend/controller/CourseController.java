package com.rodrigomv.edutrackbackend.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*")
public class CourseController {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CourseItem {
        private String id;
        private String title;
        private String code;
        private String group;
        private int studentsCount;
        private String averageGrade;
        private int progress;
        private String nextClass;
        private String status; // "activo" | "progreso"
    }

    @GetMapping
    public ResponseEntity<List<CourseItem>> getCourses() {
        List<CourseItem> courses = new ArrayList<>();
        courses.add(new CourseItem("c1", "Ingeniería de Software", "ISW-401", "Grupo A", 35, "4.2", 65, "Hoy, 14:00 - Lab 302", "activo"));
        courses.add(new CourseItem("c2", "Base de Datos II", "BBD-302", "Grupo C", 28, "3.8", 40, "Mañana, 08:00 - Virtual", "activo"));
        courses.add(new CourseItem("c3", "Sistemas Operativos", "SOP-205", "Grupo B", 32, "4.3", 80, "Jueves, 10:00 - Lab 301", "activo"));
        courses.add(new CourseItem("c4", "Inteligencia Artificial", "INT-501", "Grupo A", 24, "4.6", 25, "Viernes, 14:00 - Lab 303", "progreso"));
        courses.add(new CourseItem("c5", "Redes de Computadoras", "RED-305", "Grupo B", 30, "4.1", 55, "Lunes, 08:00 - Aula 204", "activo"));
        courses.add(new CourseItem("c6", "Programación Web", "WEB-402", "Grupo A", 40, "4.5", 70, "Miércoles, 16:00 - Lab 302", "activo"));
        return ResponseEntity.ok(courses);
    }
}
