package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.persistence.entity.Docente;
import com.rodrigomv.edutrackbackend.service.DocenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/docentes")
@RequiredArgsConstructor
public class DocenteController {
    
    private final DocenteService docenteService;
    
    @GetMapping
    public ResponseEntity<List<Docente>> findAll() {
        return ResponseEntity.ok(docenteService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Docente> findById(@PathVariable Long id) {
        return docenteService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Docente> findByCodigo(@PathVariable String codigo) {
        return docenteService.findByCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Docente> create(@RequestBody Docente docente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(docenteService.save(docente));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Docente> update(@PathVariable Long id, @RequestBody Docente docente) {
        return ResponseEntity.ok(docenteService.update(id, docente));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        docenteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}