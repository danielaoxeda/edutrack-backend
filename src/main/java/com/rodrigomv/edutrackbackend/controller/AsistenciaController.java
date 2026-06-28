package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.persistence.entity.Asistencia;
import com.rodrigomv.edutrackbackend.persistence.enums.AsistenciaEstado;
import com.rodrigomv.edutrackbackend.service.AsistenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/asistencias")
@RequiredArgsConstructor
public class AsistenciaController {
    
    private final AsistenciaService asistenciaService;
    
    @GetMapping
    public ResponseEntity<List<Asistencia>> findAll() {
        return ResponseEntity.ok(asistenciaService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Asistencia> findById(@PathVariable Long id) {
        return asistenciaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/sesion/{sesionId}")
    public ResponseEntity<List<Asistencia>> findBySesion(@PathVariable Long sesionId) {
        return ResponseEntity.ok(asistenciaService.findBySesion(sesionId));
    }
    
    @GetMapping("/matricula/{matriculaId}")
    public ResponseEntity<List<Asistencia>> findByMatricula(@PathVariable Long matriculaId) {
        return ResponseEntity.ok(asistenciaService.findByMatricula(matriculaId));
    }
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Asistencia>> findByEstado(@PathVariable AsistenciaEstado estado) {
        return ResponseEntity.ok(asistenciaService.findByEstado(estado));
    }
    
    @GetMapping("/sesion/{sesionId}/matricula/{matriculaId}")
    public ResponseEntity<Asistencia> findBySesionAndMatricula(
            @PathVariable Long sesionId, @PathVariable Long matriculaId) {
        return asistenciaService.findBySesionAndMatricula(sesionId, matriculaId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Asistencia> create(@RequestBody Asistencia asistencia) {
        return ResponseEntity.status(HttpStatus.CREATED).body(asistenciaService.save(asistencia));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Asistencia> update(@PathVariable Long id, @RequestBody Asistencia asistencia) {
        return ResponseEntity.ok(asistenciaService.update(id, asistencia));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        asistenciaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}