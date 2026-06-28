package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.persistence.entity.Subsanacion;
import com.rodrigomv.edutrackbackend.service.SubsanacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subsanaciones")
@RequiredArgsConstructor
public class SubsanacionController {
    
    private final SubsanacionService subsanacionService;
    
    @GetMapping
    public ResponseEntity<List<Subsanacion>> findAll() {
        return ResponseEntity.ok(subsanacionService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Subsanacion> findById(@PathVariable Long id) {
        return subsanacionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/entrega/{entregaId}")
    public ResponseEntity<List<Subsanacion>> findByEntrega(@PathVariable Long entregaId) {
        return ResponseEntity.ok(subsanacionService.findByEntrega(entregaId));
    }
    
    @PostMapping
    public ResponseEntity<Subsanacion> create(@RequestBody Subsanacion subsanacion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subsanacionService.save(subsanacion));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Subsanacion> update(@PathVariable Long id, @RequestBody Subsanacion subsanacion) {
        return ResponseEntity.ok(subsanacionService.update(id, subsanacion));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        subsanacionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}