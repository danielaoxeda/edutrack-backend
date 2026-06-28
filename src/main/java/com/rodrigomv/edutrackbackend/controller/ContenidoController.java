package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.persistence.entity.Contenido;
import com.rodrigomv.edutrackbackend.persistence.enums.ContenidoTipo;
import com.rodrigomv.edutrackbackend.service.ContenidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contenidos")
@RequiredArgsConstructor
public class ContenidoController {
    
    private final ContenidoService contenidoService;
    
    @GetMapping
    public ResponseEntity<List<Contenido>> findAll() {
        return ResponseEntity.ok(contenidoService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Contenido> findById(@PathVariable Long id) {
        return contenidoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/semana/{semanaId}")
    public ResponseEntity<List<Contenido>> findBySemana(@PathVariable Long semanaId) {
        return ResponseEntity.ok(contenidoService.findBySemana(semanaId));
    }
    
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Contenido>> findByTipo(@PathVariable ContenidoTipo tipo) {
        return ResponseEntity.ok(contenidoService.findByTipo(tipo));
    }
    
    @GetMapping("/visibles")
    public ResponseEntity<List<Contenido>> findVisibles() {
        return ResponseEntity.ok(contenidoService.findVisibles());
    }
    
    @PostMapping
    public ResponseEntity<Contenido> create(@RequestBody Contenido contenido) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contenidoService.save(contenido));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Contenido> update(@PathVariable Long id, @RequestBody Contenido contenido) {
        return ResponseEntity.ok(contenidoService.update(id, contenido));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        contenidoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}