package com.rodrigomv.edutrackbackend.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StudentItem {
        private String id;
        private String name;
        private String email;
        private String code;
        private String course;
        private String group;
        private double averageGrade;
        private int attendance;
        private String status; // "sobresaliente" | "regular" | "riesgo"
    }

    @GetMapping
    public ResponseEntity<List<StudentItem>> getStudents() {
        List<StudentItem> students = new ArrayList<>();
        students.add(new StudentItem("s1", "Valeria Castillo", "vcastillo@edutrack.edu", "2023-0145", "Ingeniería de Software", "Grupo A", 4.6, 98, "sobresaliente"));
        students.add(new StudentItem("s2", "Mateo Rojas", "mrojas@edutrack.edu", "2023-0211", "Sistemas Operativos", "Grupo B", 3.5, 85, "regular"));
        students.add(new StudentItem("s3", "Carlos Mendoza", "cmendoza@edutrack.edu", "2023-0089", "Ingeniería de Software", "Grupo A", 2.4, 65, "riesgo"));
        students.add(new StudentItem("s4", "Ana Rojas", "arojas@edutrack.edu", "2023-0301", "Sistemas Operativos", "Grupo B", 2.0, 70, "riesgo"));
        students.add(new StudentItem("s5", "Luis Peña", "lpena@edutrack.edu", "2023-0412", "Base de Datos II", "Grupo C", 3.8, 90, "regular"));
        students.add(new StudentItem("s6", "Lucía Méndez", "lmendez@edutrack.edu", "2023-0182", "Base de Datos II", "Grupo C", 4.8, 99, "sobresaliente"));
        students.add(new StudentItem("s7", "Sofía Castro", "scastro@edutrack.edu", "2023-0523", "Ingeniería de Software", "Grupo A", 4.3, 94, "sobresaliente"));
        students.add(new StudentItem("s8", "Diego Torres", "dtorres@edutrack.edu", "2023-0091", "Sistemas Operativos", "Grupo B", 2.9, 68, "riesgo"));
        students.add(new StudentItem("s9", "Gabriel Ruiz", "gruiz@edutrack.edu", "2023-0341", "Inteligencia Artificial", "Grupo A", 4.7, 96, "sobresaliente"));
        students.add(new StudentItem("s10", "Valentina Gómez", "vgomez@edutrack.edu", "2023-0112", "Inteligencia Artificial", "Grupo A", 3.9, 92, "regular"));
        return ResponseEntity.ok(students);
    }
}
