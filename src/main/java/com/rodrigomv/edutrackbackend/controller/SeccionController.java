package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.dto.seccion.SeccionRequestDTO;
import com.rodrigomv.edutrackbackend.dto.seccion.SeccionResponseDTO;
import com.rodrigomv.edutrackbackend.service.SeccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/secciones")
@RequiredArgsConstructor
public class SeccionController {
    
    private final SeccionService seccionService;
    
    @GetMapping
    public ResponseEntity<List<SeccionResponseDTO>> findAll() {
        return ResponseEntity.ok(seccionService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SeccionResponseDTO> findById(@PathVariable Long id) {
        return seccionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/periodo/{periodoId}")
    public ResponseEntity<List<SeccionResponseDTO>> findByPeriodo(@PathVariable Long periodoId) {
        return ResponseEntity.ok(seccionService.findByPeriodo(periodoId));
    }
    
    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<SeccionResponseDTO>> findByCurso(@PathVariable Long cursoId) {
        return ResponseEntity.ok(seccionService.findByCurso(cursoId));
    }
    
    @PostMapping
    public ResponseEntity<SeccionResponseDTO> create(@Valid @RequestBody SeccionRequestDTO seccion) {
        return ResponseEntity.status(201).body(seccionService.save(seccion));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SeccionResponseDTO> update(@PathVariable Long id, @Valid @RequestBody SeccionRequestDTO seccion) {
        return ResponseEntity.ok(seccionService.update(id, seccion));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        seccionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}