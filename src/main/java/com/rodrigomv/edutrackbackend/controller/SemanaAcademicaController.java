package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.persistence.entity.SemanaAcademica;
import com.rodrigomv.edutrackbackend.service.SemanaAcademicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/semanas")
@RequiredArgsConstructor
public class SemanaAcademicaController {
    
    private final SemanaAcademicaService semanaService;
    
    @GetMapping
    public ResponseEntity<List<SemanaAcademica>> findAll() {
        return ResponseEntity.ok(semanaService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SemanaAcademica> findById(@PathVariable Long id) {
        return semanaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/seccion/{seccionId}")
    public ResponseEntity<List<SemanaAcademica>> findBySeccion(@PathVariable Long seccionId) {
        return ResponseEntity.ok(semanaService.findBySeccion(seccionId));
    }
    
    @PostMapping
    public ResponseEntity<SemanaAcademica> create(@RequestBody SemanaAcademica semana) {
        return ResponseEntity.status(HttpStatus.CREATED).body(semanaService.save(semana));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SemanaAcademica> update(@PathVariable Long id, @RequestBody SemanaAcademica semana) {
        return ResponseEntity.ok(semanaService.update(id, semana));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        semanaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}