package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.dto.asistencia.AsistenciaRequestDTO;
import com.rodrigomv.edutrackbackend.dto.asistencia.AsistenciaResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.enums.AsistenciaEstado;
import com.rodrigomv.edutrackbackend.service.AsistenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/asistencias")
@RequiredArgsConstructor
public class AsistenciaController {
    
    private final AsistenciaService asistenciaService;
    
    @GetMapping
    public ResponseEntity<List<AsistenciaResponseDTO>> findAll() {
        return ResponseEntity.ok(asistenciaService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AsistenciaResponseDTO> findById(@PathVariable Long id) {
        return asistenciaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/sesion/{sesionId}")
    public ResponseEntity<List<AsistenciaResponseDTO>> findBySesion(@PathVariable Long sesionId) {
        return ResponseEntity.ok(asistenciaService.findBySesion(sesionId));
    }
    
    @GetMapping("/matricula/{matriculaId}")
    public ResponseEntity<List<AsistenciaResponseDTO>> findByMatricula(@PathVariable Long matriculaId) {
        return ResponseEntity.ok(asistenciaService.findByMatricula(matriculaId));
    }
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<AsistenciaResponseDTO>> findByEstado(@PathVariable AsistenciaEstado estado) {
        return ResponseEntity.ok(asistenciaService.findByEstado(estado));
    }
    
    @GetMapping("/sesion/{sesionId}/matricula/{matriculaId}")
    public ResponseEntity<AsistenciaResponseDTO> findBySesionAndMatricula(
            @PathVariable Long sesionId, @PathVariable Long matriculaId) {
        return asistenciaService.findBySesionAndMatricula(sesionId, matriculaId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<AsistenciaResponseDTO> create(@Valid @RequestBody AsistenciaRequestDTO asistencia) {
        return ResponseEntity.status(201).body(asistenciaService.save(asistencia));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<AsistenciaResponseDTO> update(@PathVariable Long id, @Valid @RequestBody AsistenciaRequestDTO asistencia) {
        return ResponseEntity.ok(asistenciaService.update(id, asistencia));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        asistenciaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}