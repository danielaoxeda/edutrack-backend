package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.dto.usuarioRol.UsuarioRolRequestDTO;
import com.rodrigomv.edutrackbackend.dto.usuarioRol.UsuarioRolResponseDTO;
import com.rodrigomv.edutrackbackend.service.UsuarioRolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/usuario-roles")
@RequiredArgsConstructor
public class UsuarioRolController {
    
    private final UsuarioRolService usuarioRolService;
    
    @GetMapping
    public ResponseEntity<List<UsuarioRolResponseDTO>> findAll() {
        return ResponseEntity.ok(usuarioRolService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRolResponseDTO> findById(@PathVariable Long id) {
        return usuarioRolService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<UsuarioRolResponseDTO>> findByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(usuarioRolService.findByUsuario(usuarioId));
    }
    
    @GetMapping("/rol/{rolId}")
    public ResponseEntity<List<UsuarioRolResponseDTO>> findByRol(@PathVariable Long rolId) {
        return ResponseEntity.ok(usuarioRolService.findByRol(rolId));
    }
    
    @PostMapping
    public ResponseEntity<UsuarioRolResponseDTO> create(@Valid @RequestBody UsuarioRolRequestDTO usuarioRol) {
        return ResponseEntity.status(201).body(usuarioRolService.save(usuarioRol));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioRolService.delete(id);
        return ResponseEntity.noContent().build();
    }
}