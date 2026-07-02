package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.dto.rolPermiso.RolPermisoRequestDTO;
import com.rodrigomv.edutrackbackend.dto.rolPermiso.RolPermisoResponseDTO;
import com.rodrigomv.edutrackbackend.service.RolPermisoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/rol-permisos")
@RequiredArgsConstructor
public class RolPermisoController {
    
    private final RolPermisoService rolPermisoService;
    
    @GetMapping
    public ResponseEntity<List<RolPermisoResponseDTO>> findAll() {
        return ResponseEntity.ok(rolPermisoService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<RolPermisoResponseDTO> findById(@PathVariable Long id) {
        return rolPermisoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/rol/{rolId}")
    public ResponseEntity<List<RolPermisoResponseDTO>> findByRol(@PathVariable Long rolId) {
        return ResponseEntity.ok(rolPermisoService.findByRol(rolId));
    }
    
    @GetMapping("/permiso/{permisoId}")
    public ResponseEntity<List<RolPermisoResponseDTO>> findByPermiso(@PathVariable Long permisoId) {
        return ResponseEntity.ok(rolPermisoService.findByPermiso(permisoId));
    }
    
    @PostMapping
    public ResponseEntity<RolPermisoResponseDTO> create(@Valid @RequestBody RolPermisoRequestDTO rolPermiso) {
        return ResponseEntity.status(201).body(rolPermisoService.save(rolPermiso));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        rolPermisoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}