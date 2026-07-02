package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.dto.rol.RolRequestDTO;
import com.rodrigomv.edutrackbackend.dto.rol.RolResponseDTO;
import com.rodrigomv.edutrackbackend.service.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolController {
    
    private final RolService rolService;
    
    @GetMapping
    public ResponseEntity<List<RolResponseDTO>> findAll() {
        return ResponseEntity.ok(rolService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<RolResponseDTO> findById(@PathVariable Long id) {
        return rolService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<RolResponseDTO> findByNombre(@PathVariable String nombre) {
        return rolService.findByNombre(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<RolResponseDTO> create(@Valid @RequestBody RolRequestDTO rol) {
        return ResponseEntity.status(201).body(rolService.save(rol));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<RolResponseDTO> update(@PathVariable Long id, @Valid @RequestBody RolRequestDTO rol) {
        return ResponseEntity.ok(rolService.update(id, rol));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        rolService.delete(id);
        return ResponseEntity.noContent().build();
    }
}