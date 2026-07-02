package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.dto.matricula.MatriculaRequestDTO;
import com.rodrigomv.edutrackbackend.dto.matricula.MatriculaResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.enums.MatriculaEstado;
import com.rodrigomv.edutrackbackend.service.MatriculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/matriculas")
@RequiredArgsConstructor
public class MatriculaController {
    
    private final MatriculaService matriculaService;
    
    @GetMapping
    public ResponseEntity<List<MatriculaResponseDTO>> findAll() {
        return ResponseEntity.ok(matriculaService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MatriculaResponseDTO> findById(@PathVariable Long id) {
        return matriculaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<List<MatriculaResponseDTO>> findByEstudiante(@PathVariable Long estudianteId) {
        return ResponseEntity.ok(matriculaService.findByEstudiante(estudianteId));
    }
    
    @GetMapping("/seccion/{seccionId}")
    public ResponseEntity<List<MatriculaResponseDTO>> findBySeccion(@PathVariable Long seccionId) {
        return ResponseEntity.ok(matriculaService.findBySeccion(seccionId));
    }
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<MatriculaResponseDTO>> findByEstado(@PathVariable MatriculaEstado estado) {
        return ResponseEntity.ok(matriculaService.findByEstado(estado));
    }
    
    @PostMapping
    public ResponseEntity<MatriculaResponseDTO> create(@Valid @RequestBody MatriculaRequestDTO matricula) {
        return ResponseEntity.status(201).body(matriculaService.save(matricula));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<MatriculaResponseDTO> update(@PathVariable Long id, @Valid @RequestBody MatriculaRequestDTO matricula) {
        return ResponseEntity.ok(matriculaService.update(id, matricula));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        matriculaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}