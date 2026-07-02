package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.dto.foro.ForoRequestDTO;
import com.rodrigomv.edutrackbackend.dto.foro.ForoResponseDTO;
import com.rodrigomv.edutrackbackend.service.ForoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/foros")
@RequiredArgsConstructor
public class ForoController {
    
    private final ForoService foroService;
    
    @GetMapping
    public ResponseEntity<List<ForoResponseDTO>> findAll() {
        return ResponseEntity.ok(foroService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ForoResponseDTO> findById(@PathVariable Long id) {
        return foroService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/semana/{semanaId}")
    public ResponseEntity<List<ForoResponseDTO>> findBySemana(@PathVariable Long semanaId) {
        return ResponseEntity.ok(foroService.findBySemana(semanaId));
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ForoResponseDTO>> findByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(foroService.findByUsuario(usuarioId));
    }
    
    @PostMapping
    public ResponseEntity<ForoResponseDTO> create(@Valid @RequestBody ForoRequestDTO foro) {
        return ResponseEntity.status(201).body(foroService.save(foro));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ForoResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ForoRequestDTO foro) {
        return ResponseEntity.ok(foroService.update(id, foro));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        foroService.delete(id);
        return ResponseEntity.noContent().build();
    }
}