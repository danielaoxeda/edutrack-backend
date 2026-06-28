package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.persistence.entity.Entrega;
import com.rodrigomv.edutrackbackend.persistence.enums.EntregaEstado;
import com.rodrigomv.edutrackbackend.service.EntregaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entregas")
@RequiredArgsConstructor
public class EntregaController {
    
    private final EntregaService entregaService;
    
    @GetMapping
    public ResponseEntity<List<Entrega>> findAll() {
        return ResponseEntity.ok(entregaService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Entrega> findById(@PathVariable Long id) {
        return entregaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/actividad/{actividadId}")
    public ResponseEntity<List<Entrega>> findByActividad(@PathVariable Long actividadId) {
        return ResponseEntity.ok(entregaService.findByActividad(actividadId));
    }
    
    @GetMapping("/matricula/{matriculaId}")
    public ResponseEntity<List<Entrega>> findByMatricula(@PathVariable Long matriculaId) {
        return ResponseEntity.ok(entregaService.findByMatricula(matriculaId));
    }
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Entrega>> findByEstado(@PathVariable EntregaEstado estado) {
        return ResponseEntity.ok(entregaService.findByEstado(estado));
    }
    
    @GetMapping("/actividad/{actividadId}/matricula/{matriculaId}")
    public ResponseEntity<Entrega> findByActividadAndMatricula(
            @PathVariable Long actividadId, @PathVariable Long matriculaId) {
        return entregaService.findByActividadAndMatricula(actividadId, matriculaId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Entrega> create(@RequestBody Entrega entrega) {
        return ResponseEntity.status(HttpStatus.CREATED).body(entregaService.save(entrega));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Entrega> update(@PathVariable Long id, @RequestBody Entrega entrega) {
        return ResponseEntity.ok(entregaService.update(id, entrega));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        entregaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}