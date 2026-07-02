package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.dto.docente.DashboardDocenteResponseDTO;
import com.rodrigomv.edutrackbackend.service.DashboardDocenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/docente")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class DashboardDocenteController {

    private final DashboardDocenteService dashboardDocenteService;

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardDocenteResponseDTO> getDashboard() {
        return ResponseEntity.ok(dashboardDocenteService.getDashboard());
    }
}
