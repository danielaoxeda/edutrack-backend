package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.dto.curso.CursoRequestDTO;
import com.rodrigomv.edutrackbackend.dto.curso.CursoResponseDTO;
import com.rodrigomv.edutrackbackend.service.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
public class CursoController {
    
    private final CursoService cursoService;
    
    @GetMapping
    public ResponseEntity<List<CursoResponseDTO>> findAll() {
        return ResponseEntity.ok(cursoService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> findById(@PathVariable Long id) {
        return cursoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<CursoResponseDTO> findByCodigo(@PathVariable String codigo) {
        return cursoService.findByCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<CursoResponseDTO> create(@Valid @RequestBody CursoRequestDTO curso) {
        return ResponseEntity.status(201).body(cursoService.save(curso));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CursoRequestDTO curso) {
        return ResponseEntity.ok(cursoService.update(id, curso));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cursoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}