package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.persistence.entity.ForoRespuesta;
import com.rodrigomv.edutrackbackend.service.ForoRespuestaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/respuestas")
@RequiredArgsConstructor
public class ForoRespuestaController {
    
    private final ForoRespuestaService respuestaService;
    
    @GetMapping
    public ResponseEntity<List<ForoRespuesta>> findAll() {
        return ResponseEntity.ok(respuestaService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ForoRespuesta> findById(@PathVariable Long id) {
        return respuestaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/foro/{foroId}")
    public ResponseEntity<List<ForoRespuesta>> findByForo(@PathVariable Long foroId) {
        return ResponseEntity.ok(respuestaService.findByForo(foroId));
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ForoRespuesta>> findByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(respuestaService.findByUsuario(usuarioId));
    }
    
    @PostMapping
    public ResponseEntity<ForoRespuesta> create(@RequestBody ForoRespuesta respuesta) {
        return ResponseEntity.status(HttpStatus.CREATED).body(respuestaService.save(respuesta));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ForoRespuesta> update(@PathVariable Long id, @RequestBody ForoRespuesta respuesta) {
        return ResponseEntity.ok(respuestaService.update(id, respuesta));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        respuestaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}