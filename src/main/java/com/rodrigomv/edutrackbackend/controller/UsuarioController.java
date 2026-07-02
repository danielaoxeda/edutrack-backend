package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.dto.usuario.UsuarioRequestDTO;
import com.rodrigomv.edutrackbackend.dto.usuario.UsuarioResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.enums.UsuarioEstado;
import com.rodrigomv.edutrackbackend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    
    private final UsuarioService usuarioService;
    
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> findById(@PathVariable Long id) {
        return usuarioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioResponseDTO> findByEmail(@PathVariable String email) {
        return usuarioService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<UsuarioResponseDTO>> findByEstado(@PathVariable UsuarioEstado estado) {
        return ResponseEntity.ok(usuarioService.findByEstado(estado));
    }
    
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> create(@Valid @RequestBody UsuarioRequestDTO usuario) {
        return ResponseEntity.status(201).body(usuarioService.save(usuario));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UsuarioRequestDTO usuario) {
        return ResponseEntity.ok(usuarioService.update(id, usuario));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}