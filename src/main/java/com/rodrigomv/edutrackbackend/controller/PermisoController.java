package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.dto.permiso.PermisoRequestDTO;
import com.rodrigomv.edutrackbackend.dto.permiso.PermisoResponseDTO;
import com.rodrigomv.edutrackbackend.service.PermisoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/permisos")
@RequiredArgsConstructor
public class PermisoController {
    
    private final PermisoService permisoService;
    
    @GetMapping
    public ResponseEntity<List<PermisoResponseDTO>> findAll() {
        return ResponseEntity.ok(permisoService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PermisoResponseDTO> findById(@PathVariable Long id) {
        return permisoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/recurso/{recurso}")
    public ResponseEntity<List<PermisoResponseDTO>> findByRecurso(@PathVariable String recurso) {
        return ResponseEntity.ok(permisoService.findByRecurso(recurso));
    }
    
    @GetMapping("/accion/{accion}")
    public ResponseEntity<List<PermisoResponseDTO>> findByAccion(@PathVariable String accion) {
        return ResponseEntity.ok(permisoService.findByAccion(accion));
    }
    
    @PostMapping
    public ResponseEntity<PermisoResponseDTO> create(@Valid @RequestBody PermisoRequestDTO permiso) {
        return ResponseEntity.status(201).body(permisoService.save(permiso));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PermisoResponseDTO> update(@PathVariable Long id, @Valid @RequestBody PermisoRequestDTO permiso) {
        return ResponseEntity.ok(permisoService.update(id, permiso));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        permisoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}