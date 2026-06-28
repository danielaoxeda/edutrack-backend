package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.persistence.entity.AlertaAcademica;
import com.rodrigomv.edutrackbackend.persistence.enums.AlertaTipo;
import com.rodrigomv.edutrackbackend.service.AlertaAcademicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alertas")
@RequiredArgsConstructor
public class AlertaAcademicaController {
    
    private final AlertaAcademicaService alertaService;
    
    @GetMapping
    public ResponseEntity<List<AlertaAcademica>> findAll() {
        return ResponseEntity.ok(alertaService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AlertaAcademica> findById(@PathVariable Long id) {
        return alertaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/matricula/{matriculaId}")
    public ResponseEntity<List<AlertaAcademica>> findByMatricula(@PathVariable Long matriculaId) {
        return ResponseEntity.ok(alertaService.findByMatricula(matriculaId));
    }
    
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<AlertaAcademica>> findByTipo(@PathVariable AlertaTipo tipo) {
        return ResponseEntity.ok(alertaService.findByTipo(tipo));
    }
    
    @PostMapping
    public ResponseEntity<AlertaAcademica> create(@RequestBody AlertaAcademica alerta) {
        return ResponseEntity.status(HttpStatus.CREATED).body(alertaService.save(alerta));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<AlertaAcademica> update(@PathVariable Long id, @RequestBody AlertaAcademica alerta) {
        return ResponseEntity.ok(alertaService.update(id, alerta));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alertaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}