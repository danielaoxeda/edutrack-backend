package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.dto.entrega.EntregaRequestDTO;
import com.rodrigomv.edutrackbackend.dto.entrega.EntregaResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.enums.EntregaEstado;
import com.rodrigomv.edutrackbackend.service.EntregaService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/entregas")
@RequiredArgsConstructor
public class EntregaController {
    
    private final EntregaService entregaService;
    
    @GetMapping
    public ResponseEntity<List<EntregaResponseDTO>> findAll() {
        return ResponseEntity.ok(entregaService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EntregaResponseDTO> findById(@PathVariable Long id) {
        return entregaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/actividad/{actividadId}")
    public ResponseEntity<List<EntregaResponseDTO>> findByActividad(@PathVariable Long actividadId) {
        return ResponseEntity.ok(entregaService.findByActividad(actividadId));
    }
    
    @GetMapping("/matricula/{matriculaId}")
    public ResponseEntity<List<EntregaResponseDTO>> findByMatricula(@PathVariable Long matriculaId) {
        return ResponseEntity.ok(entregaService.findByMatricula(matriculaId));
    }
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<EntregaResponseDTO>> findByEstado(@PathVariable EntregaEstado estado) {
        return ResponseEntity.ok(entregaService.findByEstado(estado));
    }
    
    @GetMapping("/actividad/{actividadId}/matricula/{matriculaId}")
    public ResponseEntity<EntregaResponseDTO> findByActividadAndMatricula(
            @PathVariable Long actividadId, @PathVariable Long matriculaId) {
        return entregaService.findByActividadAndMatricula(actividadId, matriculaId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<EntregaResponseDTO> create(@Valid @RequestBody EntregaRequestDTO entrega) {
        return ResponseEntity.status(201).body(entregaService.save(entrega));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<EntregaResponseDTO> update(@PathVariable Long id, @Valid @RequestBody EntregaRequestDTO entrega) {
        return ResponseEntity.ok(entregaService.update(id, entrega));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        entregaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Califica una entrega (docente)
     */
    @PutMapping("/{id}/calificar")
    @PreAuthorize("hasRole('DOCENTE') or hasRole('ADMIN')")
    public ResponseEntity<EntregaResponseDTO> calificar(
            @PathVariable Long id,
            @RequestBody CalificarRequest request
    ) {
        return ResponseEntity.ok(entregaService.calificar(id, request.getNota(), request.getComentario()));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CalificarRequest {
        private BigDecimal nota;
        private String comentario;
    }
}
