package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.dto.docente.DocenteRequestDTO;
import com.rodrigomv.edutrackbackend.dto.docente.DocenteResponseDTO;
import com.rodrigomv.edutrackbackend.service.DocenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/docentes")
@RequiredArgsConstructor
public class DocenteController {
    
    private final DocenteService docenteService;
    
    @GetMapping
    public ResponseEntity<List<DocenteResponseDTO>> findAll() {
        return ResponseEntity.ok(docenteService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DocenteResponseDTO> findById(@PathVariable Long id) {
        return docenteService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<DocenteResponseDTO> findByCodigo(@PathVariable String codigo) {
        return docenteService.findByCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<DocenteResponseDTO> create(@Valid @RequestBody DocenteRequestDTO docente) {
        return ResponseEntity.status(201).body(docenteService.save(docente));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<DocenteResponseDTO> update(@PathVariable Long id, @Valid @RequestBody DocenteRequestDTO docente) {
        return ResponseEntity.ok(docenteService.update(id, docente));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        docenteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}