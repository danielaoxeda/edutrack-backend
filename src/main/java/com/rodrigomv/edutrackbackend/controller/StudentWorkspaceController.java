package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.dto.estudiante.StudentWorkspaceResponseDTO;
import com.rodrigomv.edutrackbackend.dto.estudiante.StudentActivitySubmissionRequestDTO;
import com.rodrigomv.edutrackbackend.service.StudentWorkspaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alumno")
@RequiredArgsConstructor
public class StudentWorkspaceController {

    private final StudentWorkspaceService studentWorkspaceService;

    @GetMapping("/workspace")
    public ResponseEntity<StudentWorkspaceResponseDTO> getWorkspace() {
        return ResponseEntity.ok(studentWorkspaceService.getWorkspace());
    }

    @PostMapping("/actividades/{actividadId}/entrega")
    public ResponseEntity<StudentWorkspaceResponseDTO.DeliveryDTO> submitActivity(
            @PathVariable Long actividadId,
            @Valid @RequestBody StudentActivitySubmissionRequestDTO request
    ) {
        return ResponseEntity.ok(studentWorkspaceService.submitActivity(actividadId, request));
    }
}
