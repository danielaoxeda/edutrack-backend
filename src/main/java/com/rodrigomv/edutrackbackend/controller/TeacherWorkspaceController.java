package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.dto.docente.TeacherWorkspaceResponseDTO;
import com.rodrigomv.edutrackbackend.dto.docente.TeacherActivityOptionDTO;
import com.rodrigomv.edutrackbackend.dto.docente.TeacherActivityRequestDTO;
import com.rodrigomv.edutrackbackend.dto.actividad.ActividadResponseDTO;
import com.rodrigomv.edutrackbackend.service.TeacherActivityService;
import com.rodrigomv.edutrackbackend.service.TeacherWorkspaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/docente")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TeacherWorkspaceController {

    private final TeacherWorkspaceService teacherWorkspaceService;
    private final TeacherActivityService teacherActivityService;

    @GetMapping("/workspace")
    public ResponseEntity<TeacherWorkspaceResponseDTO> getWorkspace() {
        return ResponseEntity.ok(teacherWorkspaceService.getWorkspace());
    }

    @GetMapping("/actividad-opciones")
    public ResponseEntity<java.util.List<TeacherActivityOptionDTO>> getActivityOptions() {
        return ResponseEntity.ok(teacherActivityService.getAssignedSections());
    }

    @PostMapping("/actividades")
    public ResponseEntity<ActividadResponseDTO> createActivity(@Valid @RequestBody TeacherActivityRequestDTO request) {
        return ResponseEntity.status(201).body(teacherActivityService.create(request));
    }

    @PostMapping("/asistencia")
    public ResponseEntity<Void> saveAttendance(
            @RequestParam(required = false) String date,
            @RequestBody TeacherWorkspaceResponseDTO.AttendanceSectionDTO payload
    ) {
        teacherWorkspaceService.saveAttendance(date, payload.getAttendanceList());
        return ResponseEntity.noContent().build();
    }
}
