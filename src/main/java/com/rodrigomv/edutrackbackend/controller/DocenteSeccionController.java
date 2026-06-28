package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.persistence.entity.DocenteSeccion;
import com.rodrigomv.edutrackbackend.service.DocenteSeccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/docente-secciones")
@RequiredArgsConstructor
public class DocenteSeccionController {
    
    private final DocenteSeccionService docenteSeccionService;
    
    @GetMapping
    public ResponseEntity<List<DocenteSeccion>> findAll() {
        return ResponseEntity.ok(docenteSeccionService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DocenteSeccion> findById(@PathVariable Long id) {
        return docenteSeccionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/docente/{docenteId}")
    public ResponseEntity<List<DocenteSeccion>> findByDocente(@PathVariable Long docenteId) {
        return ResponseEntity.ok(docenteSeccionService.findByDocente(docenteId));
    }
    
    @GetMapping("/seccion/{seccionId}")
    public ResponseEntity<List<DocenteSeccion>> findBySeccion(@PathVariable Long seccionId) {
        return ResponseEntity.ok(docenteSeccionService.findBySeccion(seccionId));
    }
    
    @PostMapping
    public ResponseEntity<DocenteSeccion> create(@RequestBody DocenteSeccion docenteSeccion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(docenteSeccionService.save(docenteSeccion));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        docenteSeccionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}