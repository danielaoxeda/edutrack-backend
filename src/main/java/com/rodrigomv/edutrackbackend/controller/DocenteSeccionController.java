package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.dto.docenteSeccion.DocenteSeccionRequestDTO;
import com.rodrigomv.edutrackbackend.dto.docenteSeccion.DocenteSeccionResponseDTO;
import com.rodrigomv.edutrackbackend.service.DocenteSeccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/docente-secciones")
@RequiredArgsConstructor
public class DocenteSeccionController {
    
    private final DocenteSeccionService docenteSeccionService;
    
    @GetMapping
    public ResponseEntity<List<DocenteSeccionResponseDTO>> findAll() {
        return ResponseEntity.ok(docenteSeccionService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DocenteSeccionResponseDTO> findById(@PathVariable Long id) {
        return docenteSeccionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/docente/{docenteId}")
    public ResponseEntity<List<DocenteSeccionResponseDTO>> findByDocente(@PathVariable Long docenteId) {
        return ResponseEntity.ok(docenteSeccionService.findByDocente(docenteId));
    }
    
    @GetMapping("/seccion/{seccionId}")
    public ResponseEntity<List<DocenteSeccionResponseDTO>> findBySeccion(@PathVariable Long seccionId) {
        return ResponseEntity.ok(docenteSeccionService.findBySeccion(seccionId));
    }
    
    @PostMapping
    public ResponseEntity<DocenteSeccionResponseDTO> create(@Valid @RequestBody DocenteSeccionRequestDTO docenteSeccion) {
        return ResponseEntity.status(201).body(docenteSeccionService.save(docenteSeccion));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        docenteSeccionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}