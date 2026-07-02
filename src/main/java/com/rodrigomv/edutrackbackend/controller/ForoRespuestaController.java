package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.dto.foroRespuesta.ForoRespuestaRequestDTO;
import com.rodrigomv.edutrackbackend.dto.foroRespuesta.ForoRespuestaResponseDTO;
import com.rodrigomv.edutrackbackend.service.ForoRespuestaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/respuestas")
@RequiredArgsConstructor
public class ForoRespuestaController {
    
    private final ForoRespuestaService respuestaService;
    
    @GetMapping
    public ResponseEntity<List<ForoRespuestaResponseDTO>> findAll() {
        return ResponseEntity.ok(respuestaService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ForoRespuestaResponseDTO> findById(@PathVariable Long id) {
        return respuestaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/foro/{foroId}")
    public ResponseEntity<List<ForoRespuestaResponseDTO>> findByForo(@PathVariable Long foroId) {
        return ResponseEntity.ok(respuestaService.findByForo(foroId));
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ForoRespuestaResponseDTO>> findByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(respuestaService.findByUsuario(usuarioId));
    }
    
    @PostMapping
    public ResponseEntity<ForoRespuestaResponseDTO> create(@Valid @RequestBody ForoRespuestaRequestDTO respuesta) {
        return ResponseEntity.status(201).body(respuestaService.save(respuesta));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ForoRespuestaResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ForoRespuestaRequestDTO respuesta) {
        return ResponseEntity.ok(respuestaService.update(id, respuesta));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        respuestaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}