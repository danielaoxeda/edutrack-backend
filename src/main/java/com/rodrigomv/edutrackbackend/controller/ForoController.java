package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.persistence.entity.Foro;
import com.rodrigomv.edutrackbackend.service.ForoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foros")
@RequiredArgsConstructor
public class ForoController {
    
    private final ForoService foroService;
    
    @GetMapping
    public ResponseEntity<List<Foro>> findAll() {
        return ResponseEntity.ok(foroService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Foro> findById(@PathVariable Long id) {
        return foroService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/semana/{semanaId}")
    public ResponseEntity<List<Foro>> findBySemana(@PathVariable Long semanaId) {
        return ResponseEntity.ok(foroService.findBySemana(semanaId));
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Foro>> findByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(foroService.findByUsuario(usuarioId));
    }
    
    @PostMapping
    public ResponseEntity<Foro> create(@RequestBody Foro foro) {
        return ResponseEntity.status(HttpStatus.CREATED).body(foroService.save(foro));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Foro> update(@PathVariable Long id, @RequestBody Foro foro) {
        return ResponseEntity.ok(foroService.update(id, foro));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        foroService.delete(id);
        return ResponseEntity.noContent().build();
    }
}