package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.dto.estudiante.EstudianteRequestDTO;
import com.rodrigomv.edutrackbackend.dto.estudiante.EstudianteResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.enums.EstadoAcademico;
import com.rodrigomv.edutrackbackend.service.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {
    
    private final EstudianteService estudianteService;
    
    @GetMapping
    public ResponseEntity<List<EstudianteResponseDTO>> findAll() {
        return ResponseEntity.ok(estudianteService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EstudianteResponseDTO> findById(@PathVariable Long id) {
        return estudianteService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<EstudianteResponseDTO> findByCodigo(@PathVariable String codigo) {
        return estudianteService.findByCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<EstudianteResponseDTO>> findByEstado(@PathVariable EstadoAcademico estado) {
        return ResponseEntity.ok(estudianteService.findByEstadoAcademico(estado));
    }
    
    @PostMapping
    public ResponseEntity<EstudianteResponseDTO> create(@Valid @RequestBody EstudianteRequestDTO estudiante) {
        return ResponseEntity.status(201).body(estudianteService.save(estudiante));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<EstudianteResponseDTO> update(@PathVariable Long id, @Valid @RequestBody EstudianteRequestDTO estudiante) {
        return ResponseEntity.ok(estudianteService.update(id, estudiante));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        estudianteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}