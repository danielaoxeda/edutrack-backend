package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.persistence.entity.RolPermiso;
import com.rodrigomv.edutrackbackend.service.RolPermisoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rol-permisos")
@RequiredArgsConstructor
public class RolPermisoController {
    
    private final RolPermisoService rolPermisoService;
    
    @GetMapping
    public ResponseEntity<List<RolPermiso>> findAll() {
        return ResponseEntity.ok(rolPermisoService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<RolPermiso> findById(@PathVariable Long id) {
        return rolPermisoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/rol/{rolId}")
    public ResponseEntity<List<RolPermiso>> findByRol(@PathVariable Long rolId) {
        return ResponseEntity.ok(rolPermisoService.findByRol(rolId));
    }
    
    @GetMapping("/permiso/{permisoId}")
    public ResponseEntity<List<RolPermiso>> findByPermiso(@PathVariable Long permisoId) {
        return ResponseEntity.ok(rolPermisoService.findByPermiso(permisoId));
    }
    
    @PostMapping
    public ResponseEntity<RolPermiso> create(@RequestBody RolPermiso rolPermiso) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rolPermisoService.save(rolPermiso));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        rolPermisoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}