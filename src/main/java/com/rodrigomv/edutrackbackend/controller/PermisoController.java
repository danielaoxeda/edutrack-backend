package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.persistence.entity.Permiso;
import com.rodrigomv.edutrackbackend.service.PermisoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permisos")
@RequiredArgsConstructor
public class PermisoController {
    
    private final PermisoService permisoService;
    
    @GetMapping
    public ResponseEntity<List<Permiso>> findAll() {
        return ResponseEntity.ok(permisoService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Permiso> findById(@PathVariable Long id) {
        return permisoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/recurso/{recurso}")
    public ResponseEntity<List<Permiso>> findByRecurso(@PathVariable String recurso) {
        return ResponseEntity.ok(permisoService.findByRecurso(recurso));
    }
    
    @GetMapping("/accion/{accion}")
    public ResponseEntity<List<Permiso>> findByAccion(@PathVariable String accion) {
        return ResponseEntity.ok(permisoService.findByAccion(accion));
    }
    
    @PostMapping
    public ResponseEntity<Permiso> create(@RequestBody Permiso permiso) {
        return ResponseEntity.status(HttpStatus.CREATED).body(permisoService.save(permiso));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Permiso> update(@PathVariable Long id, @RequestBody Permiso permiso) {
        return ResponseEntity.ok(permisoService.update(id, permiso));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        permisoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}