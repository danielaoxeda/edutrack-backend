package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.persistence.entity.Matricula;
import com.rodrigomv.edutrackbackend.persistence.enums.MatriculaEstado;
import com.rodrigomv.edutrackbackend.service.MatriculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matriculas")
@RequiredArgsConstructor
public class MatriculaController {
    
    private final MatriculaService matriculaService;
    
    @GetMapping
    public ResponseEntity<List<Matricula>> findAll() {
        return ResponseEntity.ok(matriculaService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Matricula> findById(@PathVariable Long id) {
        return matriculaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<List<Matricula>> findByEstudiante(@PathVariable Long estudianteId) {
        return ResponseEntity.ok(matriculaService.findByEstudiante(estudianteId));
    }
    
    @GetMapping("/seccion/{seccionId}")
    public ResponseEntity<List<Matricula>> findBySeccion(@PathVariable Long seccionId) {
        return ResponseEntity.ok(matriculaService.findBySeccion(seccionId));
    }
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Matricula>> findByEstado(@PathVariable MatriculaEstado estado) {
        return ResponseEntity.ok(matriculaService.findByEstado(estado));
    }
    
    @PostMapping
    public ResponseEntity<Matricula> create(@RequestBody Matricula matricula) {
        return ResponseEntity.status(HttpStatus.CREATED).body(matriculaService.save(matricula));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Matricula> update(@PathVariable Long id, @RequestBody Matricula matricula) {
        return ResponseEntity.ok(matriculaService.update(id, matricula));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        matriculaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}