package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.dto.admin.AdminOverviewResponseDTO;
import com.rodrigomv.edutrackbackend.dto.admin.UserStatusRequestDTO;
import com.rodrigomv.edutrackbackend.dto.docente.DocenteRequestDTO;
import com.rodrigomv.edutrackbackend.dto.docente.DocenteResponseDTO;
import com.rodrigomv.edutrackbackend.dto.estudiante.EstudianteRequestDTO;
import com.rodrigomv.edutrackbackend.dto.estudiante.EstudianteResponseDTO;
import com.rodrigomv.edutrackbackend.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/overview")
    public ResponseEntity<AdminOverviewResponseDTO> getOverview() {
        return ResponseEntity.ok(adminService.getOverview());
    }

    @PostMapping("/docentes")
    public ResponseEntity<DocenteResponseDTO> createTeacher(@Valid @RequestBody DocenteRequestDTO request) {
        return ResponseEntity.status(201).body(adminService.createTeacher(request));
    }

    @PostMapping("/estudiantes")
    public ResponseEntity<EstudianteResponseDTO> createStudent(@Valid @RequestBody EstudianteRequestDTO request) {
        return ResponseEntity.status(201).body(adminService.createStudent(request));
    }

    @PatchMapping("/docentes/{docenteId}/estado")
    public ResponseEntity<Void> updateTeacherStatus(
            @PathVariable Long docenteId,
            @Valid @RequestBody UserStatusRequestDTO request
    ) {
        adminService.updateTeacherStatus(docenteId, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/estudiantes/{estudianteId}/estado")
    public ResponseEntity<Void> updateStudentStatus(
            @PathVariable Long estudianteId,
            @Valid @RequestBody UserStatusRequestDTO request
    ) {
        adminService.updateStudentStatus(estudianteId, request);
        return ResponseEntity.noContent().build();
    }
}
