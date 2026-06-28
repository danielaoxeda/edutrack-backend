package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.persistence.entity.PeriodoAcademico;
import com.rodrigomv.edutrackbackend.persistence.enums.PeriodoEstado;
import com.rodrigomv.edutrackbackend.service.PeriodoAcademicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/periodos")
@RequiredArgsConstructor
public class PeriodoAcademicoController {
    
    private final PeriodoAcademicoService periodoService;
    
    @GetMapping
    public ResponseEntity<List<PeriodoAcademico>> findAll() {
        return ResponseEntity.ok(periodoService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PeriodoAcademico> findById(@PathVariable Long id) {
        return periodoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<PeriodoAcademico> findByNombre(@PathVariable String nombre) {
        return periodoService.findByNombre(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PeriodoAcademico>> findByEstado(@PathVariable PeriodoEstado estado) {
        return ResponseEntity.ok(periodoService.findByEstado(estado));
    }
    
    @GetMapping("/activo")
    public ResponseEntity<PeriodoAcademico> findActivo() {
        return periodoService.findActivo()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<PeriodoAcademico> create(@RequestBody PeriodoAcademico periodo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(periodoService.save(periodo));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PeriodoAcademico> update(@PathVariable Long id, @RequestBody PeriodoAcademico periodo) {
        return ResponseEntity.ok(periodoService.update(id, periodo));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        periodoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}