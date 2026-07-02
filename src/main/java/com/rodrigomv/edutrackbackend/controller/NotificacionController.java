package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.dto.notificacion.NotificacionRequestDTO;
import com.rodrigomv.edutrackbackend.dto.notificacion.NotificacionResponseDTO;
import com.rodrigomv.edutrackbackend.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {
    
    private final NotificacionService notificacionService;
    
    @GetMapping
    public ResponseEntity<List<NotificacionResponseDTO>> findAll() {
        return ResponseEntity.ok(notificacionService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<NotificacionResponseDTO> findById(@PathVariable Long id) {
        return notificacionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<NotificacionResponseDTO>> findByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificacionService.findByUsuario(usuarioId));
    }
    
    @GetMapping("/usuario/{usuarioId}/no-leidas")
    public ResponseEntity<List<NotificacionResponseDTO>> findNoLeidas(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificacionService.findNoLeidas(usuarioId));
    }
    
    @GetMapping("/usuario/{usuarioId}/no-leidas/count")
    public ResponseEntity<Long> countNoLeidas(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificacionService.countNoLeidas(usuarioId));
    }
    
    @PostMapping
    public ResponseEntity<NotificacionResponseDTO> create(@Valid @RequestBody NotificacionRequestDTO notificacion) {
        return ResponseEntity.status(201).body(notificacionService.save(notificacion));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<NotificacionResponseDTO> update(@PathVariable Long id, @Valid @RequestBody NotificacionRequestDTO notificacion) {
        return ResponseEntity.ok(notificacionService.update(id, notificacion));
    }
    
    @PatchMapping("/{id}/leida")
    public ResponseEntity<NotificacionResponseDTO> marcarLeida(@PathVariable Long id) {
        return ResponseEntity.ok(notificacionService.marcarLeida(id));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        notificacionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}