package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.dto.estudiante.StudentWorkspaceResponseDTO;
import com.rodrigomv.edutrackbackend.service.StudentWorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
