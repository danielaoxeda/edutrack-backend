package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.persistence.entity.CriterioEvaluacion;
import com.rodrigomv.edutrackbackend.service.CriterioEvaluacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/criterios")
@RequiredArgsConstructor
public class CriterioEvaluacionController {
    
    private final CriterioEvaluacionService criterioService;
    
    @GetMapping
    public ResponseEntity<List<CriterioEvaluacion>> findAll() {
        return ResponseEntity.ok(criterioService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CriterioEvaluacion> findById(@PathVariable Long id) {
        return criterioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/seccion/{seccionId}")
    public ResponseEntity<List<CriterioEvaluacion>> findBySeccion(@PathVariable Long seccionId) {
        return ResponseEntity.ok(criterioService.findBySeccion(seccionId));
    }
    
    @PostMapping
    public ResponseEntity<CriterioEvaluacion> create(@RequestBody CriterioEvaluacion criterio) {
        return ResponseEntity.status(HttpStatus.CREATED).body(criterioService.save(criterio));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CriterioEvaluacion> update(@PathVariable Long id, @RequestBody CriterioEvaluacion criterio) {
        return ResponseEntity.ok(criterioService.update(id, criterio));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        criterioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}