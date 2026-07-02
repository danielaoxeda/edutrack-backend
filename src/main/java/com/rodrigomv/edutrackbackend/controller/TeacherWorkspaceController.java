package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.dto.docente.TeacherWorkspaceResponseDTO;
import com.rodrigomv.edutrackbackend.service.TeacherWorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/docente")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TeacherWorkspaceController {

    private final TeacherWorkspaceService teacherWorkspaceService;

    @GetMapping("/workspace")
    public ResponseEntity<TeacherWorkspaceResponseDTO> getWorkspace() {
        return ResponseEntity.ok(teacherWorkspaceService.getWorkspace());
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
