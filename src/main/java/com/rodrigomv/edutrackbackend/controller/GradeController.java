package com.rodrigomv.edutrackbackend.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/grades")
@CrossOrigin(origins = "*")
public class GradeController {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GradeBookItem {
        private String id;
        private String name;
        private String code;
        private String course;
        private String group;
        private double pc1;
        private double pc2;
        private double parcial;
        private double finalExam;
        private double average;
        private String status; // "aprobado" | "reprobado" | "pendiente"
    }

    private static final List<GradeBookItem> grades = new ArrayList<>();

    static {
        grades.add(new GradeBookItem("g1", "Valeria Castillo", "2023-0145", "Ingeniería de Software", "Grupo A", 4.8, 4.5, 4.3, 4.8, 4.6, "aprobado"));
        grades.add(new GradeBookItem("g2", "Mateo Rojas", "2023-0211", "Sistemas Operativos", "Grupo B", 3.5, 3.2, 3.0, 3.8, 3.5, "aprobado"));
        grades.add(new GradeBookItem("g3", "Carlos Mendoza", "2023-0089", "Ingeniería de Software", "Grupo A", 2.5, 2.8, 2.0, 2.3, 2.4, "reprobado"));
        grades.add(new GradeBookItem("g4", "Ana Rojas", "2023-0301", "Sistemas Operativos", "Grupo B", 2.0, 1.8, 2.2, 2.0, 2.0, "reprobado"));
        grades.add(new GradeBookItem("g5", "Luis Peña", "2023-0412", "Base de Datos II", "Grupo C", 4.0, 3.8, 3.5, 4.0, 3.8, "aprobado"));
        grades.add(new GradeBookItem("g6", "Lucía Méndez", "2023-0182", "Base de Datos II", "Grupo C", 4.8, 4.9, 4.5, 4.9, 4.8, "aprobado"));
        grades.add(new GradeBookItem("g7", "Sofía Castro", "2023-0523", "Ingeniería de Software", "Grupo A", 4.5, 4.0, 4.2, 4.5, 4.3, "aprobado"));
        grades.add(new GradeBookItem("g8", "Diego Torres", "2023-0091", "Sistemas Operativos", "Grupo B", 3.0, 2.8, 2.5, 3.1, 2.9, "reprobado"));
        grades.add(new GradeBookItem("g9", "Gabriel Ruiz", "2023-0341", "Inteligencia Artificial", "Grupo A", 4.6, 4.8, 4.5, 4.8, 4.7, "aprobado"));
        grades.add(new GradeBookItem("g10", "Valentina Gómez", "2023-0112", "Inteligencia Artificial", "Grupo A", 3.8, 4.0, 3.5, 4.1, 3.9, "aprobado"));
    }

    @GetMapping
    public ResponseEntity<List<GradeBookItem>> getGrades() {
        return ResponseEntity.ok(grades);
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<GradeBookItem> updateGrades(
            @PathVariable String studentId,
            @RequestBody GradeBookItem updatedGrade
    ) {
        for (int i = 0; i < grades.size(); i++) {
            GradeBookItem item = grades.get(i);
            if (item.getId().equals(studentId)) {
                // Keep name and other non-editable fields
                updatedGrade.setId(studentId);
                updatedGrade.setName(item.getName());
                updatedGrade.setCode(item.getCode());
                updatedGrade.setCourse(item.getCourse());
                updatedGrade.setGroup(item.getGroup());
                
                // Recalculate average
                double average = (updatedGrade.getPc1() * 0.15) + (updatedGrade.getPc2() * 0.15) + (updatedGrade.getParcial() * 0.3) + (updatedGrade.getFinalExam() * 0.4);
                updatedGrade.setAverage(Math.round(average * 10.0) / 10.0);
                updatedGrade.setStatus(updatedGrade.getAverage() >= 3.0 ? "aprobado" : "reprobado");
                
                grades.set(i, updatedGrade);
                return ResponseEntity.ok(updatedGrade);
            }
        }
        return ResponseEntity.notFound().build();
    }
}
