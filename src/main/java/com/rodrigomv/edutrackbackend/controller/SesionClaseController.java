package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.dto.sesionClase.SesionClaseRequestDTO;
import com.rodrigomv.edutrackbackend.dto.sesionClase.SesionClaseResponseDTO;
import com.rodrigomv.edutrackbackend.service.SesionClaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sesiones")
@RequiredArgsConstructor
public class SesionClaseController {
    
    private final SesionClaseService sesionService;
    
    @GetMapping
    public ResponseEntity<List<SesionClaseResponseDTO>> findAll() {
        return ResponseEntity.ok(sesionService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SesionClaseResponseDTO> findById(@PathVariable Long id) {
        return sesionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/semana/{semanaId}")
    public ResponseEntity<List<SesionClaseResponseDTO>> findBySemana(@PathVariable Long semanaId) {
        return ResponseEntity.ok(sesionService.findBySemana(semanaId));
    }
    
    @GetMapping("/fecha")
    public ResponseEntity<List<SesionClaseResponseDTO>> findByFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(sesionService.findByFecha(fecha));
    }
    
    @GetMapping("/fecha/rango")
    public ResponseEntity<List<SesionClaseResponseDTO>> findByFechaBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(sesionService.findByFechaBetween(inicio, fin));
    }
    
    @PostMapping
    public ResponseEntity<SesionClaseResponseDTO> create(@Valid @RequestBody SesionClaseRequestDTO sesion) {
        return ResponseEntity.status(201).body(sesionService.save(sesion));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SesionClaseResponseDTO> update(@PathVariable Long id, @Valid @RequestBody SesionClaseRequestDTO sesion) {
        return ResponseEntity.ok(sesionService.update(id, sesion));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        sesionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}