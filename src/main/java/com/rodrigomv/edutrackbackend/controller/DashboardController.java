package com.rodrigomv.edutrackbackend.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StatItem {
        private String label;
        private Object value;
        private String subtext;
        private String iconName;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AcademicAlertItem {
        private String id;
        private String type;
        private String title;
        private String description;
        private String actionLabel;
    }

    @GetMapping("/stats")
    public ResponseEntity<List<StatItem>> getStats() {
        List<StatItem> stats = new ArrayList<>();
        stats.add(new StatItem("Cursos Activos", 6, null, "BookOpen"));
        stats.add(new StatItem("Estudiantes Totales", 150, null, "Users"));
        stats.add(new StatItem("Tareas Pendientes", 24, null, "FileText"));
        stats.add(new StatItem("Eval. por Revisar", 12, null, "GraduationCap"));
        stats.add(new StatItem("Promedio General", "4.2", "/ 5.0", "TrendingUp"));
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/alerts")
    public ResponseEntity<List<AcademicAlertItem>> getAlerts() {
        List<AcademicAlertItem> alerts = new ArrayList<>();
        alerts.add(new AcademicAlertItem("a1", "risk", "Estudiantes en Riesgo (3)", "Bajo rendimiento consecutivo en Base de Datos II.", "Ver detalles"));
        alerts.add(new AcademicAlertItem("a2", "overdue", "Tareas Vencidas (8)", "Entregas no recibidas para Taller 2 de SO.", "Enviar recordatorio"));
        return ResponseEntity.ok(alerts);
    }
}
