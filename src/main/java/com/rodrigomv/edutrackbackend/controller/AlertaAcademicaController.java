package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.dto.alertaAcademica.AlertaAcademicaRequestDTO;
import com.rodrigomv.edutrackbackend.dto.alertaAcademica.AlertaAcademicaResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.enums.AlertaTipo;
import com.rodrigomv.edutrackbackend.service.AlertaAcademicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/alertas")
@RequiredArgsConstructor
public class AlertaAcademicaController {
    
    private final AlertaAcademicaService alertaService;
    
    @GetMapping
    public ResponseEntity<List<AlertaAcademicaResponseDTO>> findAll() {
        return ResponseEntity.ok(alertaService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AlertaAcademicaResponseDTO> findById(@PathVariable Long id) {
        return alertaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/matricula/{matriculaId}")
    public ResponseEntity<List<AlertaAcademicaResponseDTO>> findByMatricula(@PathVariable Long matriculaId) {
        return ResponseEntity.ok(alertaService.findByMatricula(matriculaId));
    }
    
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<AlertaAcademicaResponseDTO>> findByTipo(@PathVariable AlertaTipo tipo) {
        return ResponseEntity.ok(alertaService.findByTipo(tipo));
    }
    
    @PostMapping
    public ResponseEntity<AlertaAcademicaResponseDTO> create(@Valid @RequestBody AlertaAcademicaRequestDTO alerta) {
        return ResponseEntity.status(201).body(alertaService.save(alerta));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<AlertaAcademicaResponseDTO> update(@PathVariable Long id, @Valid @RequestBody AlertaAcademicaRequestDTO alerta) {
        return ResponseEntity.ok(alertaService.update(id, alerta));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alertaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}