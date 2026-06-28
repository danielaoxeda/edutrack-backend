package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.persistence.entity.UsuarioRol;
import com.rodrigomv.edutrackbackend.service.UsuarioRolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario-roles")
@RequiredArgsConstructor
public class UsuarioRolController {
    
    private final UsuarioRolService usuarioRolService;
    
    @GetMapping
    public ResponseEntity<List<UsuarioRol>> findAll() {
        return ResponseEntity.ok(usuarioRolService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRol> findById(@PathVariable Long id) {
        return usuarioRolService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<UsuarioRol>> findByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(usuarioRolService.findByUsuario(usuarioId));
    }
    
    @GetMapping("/rol/{rolId}")
    public ResponseEntity<List<UsuarioRol>> findByRol(@PathVariable Long rolId) {
        return ResponseEntity.ok(usuarioRolService.findByRol(rolId));
    }
    
    @PostMapping
    public ResponseEntity<UsuarioRol> create(@RequestBody UsuarioRol usuarioRol) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRolService.save(usuarioRol));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioRolService.delete(id);
        return ResponseEntity.noContent().build();
    }
}