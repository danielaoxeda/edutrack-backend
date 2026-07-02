package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.dto.criterioEvaluacion.CriterioEvaluacionRequestDTO;
import com.rodrigomv.edutrackbackend.dto.criterioEvaluacion.CriterioEvaluacionResponseDTO;
import com.rodrigomv.edutrackbackend.service.CriterioEvaluacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/criterios")
@RequiredArgsConstructor
public class CriterioEvaluacionController {
    
    private final CriterioEvaluacionService criterioService;
    
    @GetMapping
    public ResponseEntity<List<CriterioEvaluacionResponseDTO>> findAll() {
        return ResponseEntity.ok(criterioService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CriterioEvaluacionResponseDTO> findById(@PathVariable Long id) {
        return criterioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/seccion/{seccionId}")
    public ResponseEntity<List<CriterioEvaluacionResponseDTO>> findBySeccion(@PathVariable Long seccionId) {
        return ResponseEntity.ok(criterioService.findBySeccion(seccionId));
    }
    
    @PostMapping
    public ResponseEntity<CriterioEvaluacionResponseDTO> create(@Valid @RequestBody CriterioEvaluacionRequestDTO criterio) {
        return ResponseEntity.status(201).body(criterioService.save(criterio));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CriterioEvaluacionResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CriterioEvaluacionRequestDTO criterio) {
        return ResponseEntity.ok(criterioService.update(id, criterio));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        criterioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}