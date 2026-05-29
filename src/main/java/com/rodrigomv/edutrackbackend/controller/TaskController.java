package com.rodrigomv.edutrackbackend.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TaskItem {
        private String id;
        private String name;
        private String course;
        private String group;
        private String publishedDate;
        private String limitDate;
        private int receivedCount;
        private int totalCount;
        private String status; // "activo" | "calificando" | "evaluado"
    }

    private static final List<TaskItem> tasks = new ArrayList<>();

    static {
        tasks.add(new TaskItem("t1", "Ensayo Final: Historia Moderna", "Ingeniería de Software", "Grupo A", "10/10", "25/10", 28, 30, "activo"));
        tasks.add(new TaskItem("t2", "Proyecto de Ciencias: Modelo Físico", "Base de Datos II", "Grupo C", "05/10", "15/10", 30, 30, "calificando"));
        tasks.add(new TaskItem("t3", "Taller 3: Gestión de Memoria", "Sistemas Operativos", "Grupo B", "12/10", "28/10", 15, 32, "activo"));
        tasks.add(new TaskItem("t4", "Proyecto Final: Red Neuronal", "Inteligencia Artificial", "Grupo A", "20/10", "10/11", 5, 24, "activo"));
        tasks.add(new TaskItem("t5", "Práctica Evaluada 2: SQL Avanzado", "Base de Datos II", "Grupo C", "01/10", "10/10", 28, 28, "evaluado"));
    }

    @GetMapping
    public ResponseEntity<List<TaskItem>> getTasks() {
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<TaskItem> createTask(@RequestBody TaskItem newTask) {
        String id = "t" + (tasks.size() + 1);
        newTask.setId(id);
        newTask.setReceivedCount(0);
        newTask.setTotalCount(30);
        newTask.setStatus("activo");
        tasks.add(0, newTask); // Add at the beginning
        return ResponseEntity.ok(newTask);
    }
}
