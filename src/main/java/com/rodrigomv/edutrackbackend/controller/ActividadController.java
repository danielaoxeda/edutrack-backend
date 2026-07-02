package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.dto.actividad.ActividadRequestDTO;
import com.rodrigomv.edutrackbackend.dto.actividad.ActividadResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.enums.ActividadTipo;
import com.rodrigomv.edutrackbackend.service.ActividadService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/actividades")
@RequiredArgsConstructor
public class ActividadController {
    
    private final ActividadService actividadService;
    
    @GetMapping
    public ResponseEntity<List<ActividadResponseDTO>> findAll() {
        return ResponseEntity.ok(actividadService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ActividadResponseDTO> findById(@PathVariable Long id) {
        return actividadService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/semana/{semanaId}")
    public ResponseEntity<List<ActividadResponseDTO>> findBySemana(@PathVariable Long semanaId) {
        return ResponseEntity.ok(actividadService.findBySemana(semanaId));
    }
    
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<ActividadResponseDTO>> findByTipo(@PathVariable ActividadTipo tipo) {
        return ResponseEntity.ok(actividadService.findByTipo(tipo));
    }
    
    @GetMapping("/visibles")
    public ResponseEntity<List<ActividadResponseDTO>> findVisibles() {
        return ResponseEntity.ok(actividadService.findVisibles());
    }
    
    @GetMapping("/fecha")
    public ResponseEntity<List<ActividadResponseDTO>> findByFechaLimite(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(actividadService.findByFechaLimiteBetween(inicio, fin));
    }
    
    @PostMapping
    public ResponseEntity<ActividadResponseDTO> create(@Valid @RequestBody ActividadRequestDTO actividad) {
        return ResponseEntity.status(201).body(actividadService.save(actividad));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ActividadResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ActividadRequestDTO actividad) {
        return ResponseEntity.ok(actividadService.update(id, actividad));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        actividadService.delete(id);
        return ResponseEntity.noContent().build();
    }
}