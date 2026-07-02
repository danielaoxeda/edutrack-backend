package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.dto.contenido.ContenidoRequestDTO;
import com.rodrigomv.edutrackbackend.dto.contenido.ContenidoResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.enums.ContenidoTipo;
import com.rodrigomv.edutrackbackend.service.ContenidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/contenidos")
@RequiredArgsConstructor
public class ContenidoController {
    
    private final ContenidoService contenidoService;
    
    @GetMapping
    public ResponseEntity<List<ContenidoResponseDTO>> findAll() {
        return ResponseEntity.ok(contenidoService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ContenidoResponseDTO> findById(@PathVariable Long id) {
        return contenidoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/semana/{semanaId}")
    public ResponseEntity<List<ContenidoResponseDTO>> findBySemana(@PathVariable Long semanaId) {
        return ResponseEntity.ok(contenidoService.findBySemana(semanaId));
    }
    
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<ContenidoResponseDTO>> findByTipo(@PathVariable ContenidoTipo tipo) {
        return ResponseEntity.ok(contenidoService.findByTipo(tipo));
    }
    
    @GetMapping("/visibles")
    public ResponseEntity<List<ContenidoResponseDTO>> findVisibles() {
        return ResponseEntity.ok(contenidoService.findVisibles());
    }
    
    @PostMapping
    public ResponseEntity<ContenidoResponseDTO> create(@Valid @RequestBody ContenidoRequestDTO contenido) {
        return ResponseEntity.status(201).body(contenidoService.save(contenido));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ContenidoResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ContenidoRequestDTO contenido) {
        return ResponseEntity.ok(contenidoService.update(id, contenido));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        contenidoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}