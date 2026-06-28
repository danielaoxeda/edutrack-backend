package com.rodrigomv.edutrackbackend.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AttendanceRegistryItem {
        private String id;
        private String name;
        private String email;
        private String code;
        private String course;
        private String group;
        private int attendance; // accumulated %
        private String todayStatus; // "presente" | "tardanza" | "falta"
    }

    private static final List<AttendanceRegistryItem> attendance = new ArrayList<>();

    static {
        attendance.add(new AttendanceRegistryItem("a1", "Valeria Castillo", "vcastillo@edutrack.edu", "2023-0145", "Ingeniería de Software", "Grupo A", 98, "presente"));
        attendance.add(new AttendanceRegistryItem("a2", "Mateo Rojas", "mrojas@edutrack.edu", "2023-0211", "Sistemas Operativos", "Grupo B", 85, "tardanza"));
        attendance.add(new AttendanceRegistryItem("a3", "Carlos Mendoza", "cmendoza@edutrack.edu", "2023-0089", "Ingeniería de Software", "Grupo A", 65, "falta"));
        attendance.add(new AttendanceRegistryItem("a4", "Ana Rojas", "arojas@edutrack.edu", "2023-0301", "Sistemas Operativos", "Grupo B", 70, "falta"));
        attendance.add(new AttendanceRegistryItem("a5", "Luis Peña", "lpena@edutrack.edu", "2023-0412", "Base de Datos II", "Grupo C", 90, "presente"));
        attendance.add(new AttendanceRegistryItem("a6", "Lucía Méndez", "lmendez@edutrack.edu", "2023-0182", "Base de Datos II", "Grupo C", 99, "presente"));
        attendance.add(new AttendanceRegistryItem("a7", "Sofía Castro", "scastro@edutrack.edu", "2023-0523", "Ingeniería de Software", "Grupo A", 94, "presente"));
        attendance.add(new AttendanceRegistryItem("a8", "Diego Torres", "dtorres@edutrack.edu", "2023-0091", "Sistemas Operativos", "Grupo B", 68, "tardanza"));
        attendance.add(new AttendanceRegistryItem("a9", "Gabriel Ruiz", "gruiz@edutrack.edu", "2023-0341", "Inteligencia Artificial", "Grupo A", 96, "presente"));
        attendance.add(new AttendanceRegistryItem("a10", "Valentina Gómez", "vgomez@edutrack.edu", "2023-0112", "Inteligencia Artificial", "Grupo A", 92, "presente"));
    }

    @GetMapping
    public ResponseEntity<List<AttendanceRegistryItem>> getAttendance() {
        return ResponseEntity.ok(attendance);
    }

    @PostMapping("/{studentId}")
    public ResponseEntity<AttendanceRegistryItem> updateTodayStatus(
            @PathVariable String studentId,
            @RequestParam String status
    ) {
        for (AttendanceRegistryItem item : attendance) {
            if (item.getId().equals(studentId)) {
                item.setTodayStatus(status);
                return ResponseEntity.ok(item);
            }
        }
        return ResponseEntity.notFound().build();
    }
}
