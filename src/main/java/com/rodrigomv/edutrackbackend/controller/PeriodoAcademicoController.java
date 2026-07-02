package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.dto.periodoAcademico.PeriodoAcademicoRequestDTO;
import com.rodrigomv.edutrackbackend.dto.periodoAcademico.PeriodoAcademicoResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.enums.PeriodoEstado;
import com.rodrigomv.edutrackbackend.service.PeriodoAcademicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/periodos")
@RequiredArgsConstructor
public class PeriodoAcademicoController {
    
    private final PeriodoAcademicoService periodoService;
    
    @GetMapping
    public ResponseEntity<List<PeriodoAcademicoResponseDTO>> findAll() {
        return ResponseEntity.ok(periodoService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PeriodoAcademicoResponseDTO> findById(@PathVariable Long id) {
        return periodoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<PeriodoAcademicoResponseDTO> findByNombre(@PathVariable String nombre) {
        return periodoService.findByNombre(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PeriodoAcademicoResponseDTO>> findByEstado(@PathVariable PeriodoEstado estado) {
        return ResponseEntity.ok(periodoService.findByEstado(estado));
    }
    
    @GetMapping("/activo")
    public ResponseEntity<PeriodoAcademicoResponseDTO> findActivo() {
        return periodoService.findActivo()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<PeriodoAcademicoResponseDTO> create(@Valid @RequestBody PeriodoAcademicoRequestDTO periodo) {
        return ResponseEntity.status(201).body(periodoService.save(periodo));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PeriodoAcademicoResponseDTO> update(@PathVariable Long id, @Valid @RequestBody PeriodoAcademicoRequestDTO periodo) {
        return ResponseEntity.ok(periodoService.update(id, periodo));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        periodoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}