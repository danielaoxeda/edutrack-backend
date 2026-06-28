package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.persistence.entity.SesionClase;
import com.rodrigomv.edutrackbackend.service.SesionClaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sesiones")
@RequiredArgsConstructor
public class SesionClaseController {
    
    private final SesionClaseService sesionService;
    
    @GetMapping
    public ResponseEntity<List<SesionClase>> findAll() {
        return ResponseEntity.ok(sesionService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SesionClase> findById(@PathVariable Long id) {
        return sesionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/semana/{semanaId}")
    public ResponseEntity<List<SesionClase>> findBySemana(@PathVariable Long semanaId) {
        return ResponseEntity.ok(sesionService.findBySemana(semanaId));
    }
    
    @GetMapping("/fecha")
    public ResponseEntity<List<SesionClase>> findByFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(sesionService.findByFecha(fecha));
    }
    
    @GetMapping("/fecha/rango")
    public ResponseEntity<List<SesionClase>> findByFechaBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(sesionService.findByFechaBetween(inicio, fin));
    }
    
    @PostMapping
    public ResponseEntity<SesionClase> create(@RequestBody SesionClase sesion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sesionService.save(sesion));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SesionClase> update(@PathVariable Long id, @RequestBody SesionClase sesion) {
        return ResponseEntity.ok(sesionService.update(id, sesion));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        sesionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}