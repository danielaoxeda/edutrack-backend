package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.dto.semanaAcademica.SemanaAcademicaRequestDTO;
import com.rodrigomv.edutrackbackend.dto.semanaAcademica.SemanaAcademicaResponseDTO;
import com.rodrigomv.edutrackbackend.service.SemanaAcademicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/semanas")
@RequiredArgsConstructor
public class SemanaAcademicaController {
    
    private final SemanaAcademicaService semanaService;
    
    @GetMapping
    public ResponseEntity<List<SemanaAcademicaResponseDTO>> findAll() {
        return ResponseEntity.ok(semanaService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SemanaAcademicaResponseDTO> findById(@PathVariable Long id) {
        return semanaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/seccion/{seccionId}")
    public ResponseEntity<List<SemanaAcademicaResponseDTO>> findBySeccion(@PathVariable Long seccionId) {
        return ResponseEntity.ok(semanaService.findBySeccion(seccionId));
    }
    
    @PostMapping
    public ResponseEntity<SemanaAcademicaResponseDTO> create(@Valid @RequestBody SemanaAcademicaRequestDTO semana) {
        return ResponseEntity.status(201).body(semanaService.save(semana));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SemanaAcademicaResponseDTO> update(@PathVariable Long id, @Valid @RequestBody SemanaAcademicaRequestDTO semana) {
        return ResponseEntity.ok(semanaService.update(id, semana));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        semanaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}