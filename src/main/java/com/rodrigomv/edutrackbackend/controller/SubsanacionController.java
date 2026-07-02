package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.dto.subsanacion.SubsanacionRequestDTO;
import com.rodrigomv.edutrackbackend.dto.subsanacion.SubsanacionResponseDTO;
import com.rodrigomv.edutrackbackend.service.SubsanacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/subsanaciones")
@RequiredArgsConstructor
public class SubsanacionController {
    
    private final SubsanacionService subsanacionService;
    
    @GetMapping
    public ResponseEntity<List<SubsanacionResponseDTO>> findAll() {
        return ResponseEntity.ok(subsanacionService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SubsanacionResponseDTO> findById(@PathVariable Long id) {
        return subsanacionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/entrega/{entregaId}")
    public ResponseEntity<List<SubsanacionResponseDTO>> findByEntrega(@PathVariable Long entregaId) {
        return ResponseEntity.ok(subsanacionService.findByEntrega(entregaId));
    }
    
    @PostMapping
    public ResponseEntity<SubsanacionResponseDTO> create(@Valid @RequestBody SubsanacionRequestDTO subsanacion) {
        return ResponseEntity.status(201).body(subsanacionService.save(subsanacion));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SubsanacionResponseDTO> update(@PathVariable Long id, @Valid @RequestBody SubsanacionRequestDTO subsanacion) {
        return ResponseEntity.ok(subsanacionService.update(id, subsanacion));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        subsanacionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}