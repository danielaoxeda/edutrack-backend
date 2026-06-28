package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.persistence.entity.Seccion;
import com.rodrigomv.edutrackbackend.service.SeccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/secciones")
@RequiredArgsConstructor
public class SeccionController {
    
    private final SeccionService seccionService;
    
    @GetMapping
    public ResponseEntity<List<Seccion>> findAll() {
        return ResponseEntity.ok(seccionService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Seccion> findById(@PathVariable Long id) {
        return seccionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/periodo/{periodoId}")
    public ResponseEntity<List<Seccion>> findByPeriodo(@PathVariable Long periodoId) {
        return ResponseEntity.ok(seccionService.findByPeriodo(periodoId));
    }
    
    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<Seccion>> findByCurso(@PathVariable Long cursoId) {
        return ResponseEntity.ok(seccionService.findByCurso(cursoId));
    }
    
    @PostMapping
    public ResponseEntity<Seccion> create(@RequestBody Seccion seccion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(seccionService.save(seccion));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Seccion> update(@PathVariable Long id, @RequestBody Seccion seccion) {
        return ResponseEntity.ok(seccionService.update(id, seccion));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        seccionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}