package com.rodrigomv.edutrackbackend.controller;

import com.rodrigomv.edutrackbackend.service.CalificacionService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/calificaciones")
@CrossOrigin(origins = "*")
public class CalificacionController {

    private final CalificacionService calificacionService;

    public CalificacionController(CalificacionService calificacionService) {
        this.calificacionService = calificacionService;
    }

    /**
     * Obtiene la boleta de notas de un estudiante en una sección
     */
    @GetMapping("/estudiante/{estudianteId}/seccion/{seccionId}")
    public ResponseEntity<Map<String, Object>> obtenerBoletaEstudiante(
            @PathVariable Long estudianteId,
            @PathVariable Long seccionId
    ) {
        Map<String, Object> boleta = calificacionService.obtenerBoletaEstudiante(estudianteId, seccionId);
        return ResponseEntity.ok(boleta);
    }

    /**
     * Obtiene el resumen de calificaciones de una sección (para docente)
     */
    @GetMapping("/seccion/{seccionId}")
    @PreAuthorize("hasRole('DOCENTE') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> obtenerResumenSeccion(@PathVariable Long seccionId) {
        Map<String, Object> resumen = calificacionService.obtenerResumenSeccion(seccionId);
        return ResponseEntity.ok(resumen);
    }

    /**
     * Obtiene estadísticas de una sección
     */
    @GetMapping("/seccion/{seccionId}/estadisticas")
    @PreAuthorize("hasRole('DOCENTE') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticasSeccion(@PathVariable Long seccionId) {
        Map<String, Object> stats = calificacionService.obtenerEstadisticasSeccion(seccionId);
        return ResponseEntity.ok(stats);
    }

    /**
     * Califica una entrega
     */
    @PutMapping("/entrega/{entregaId}/calificar")
    @PreAuthorize("hasRole('DOCENTE') or hasRole('ADMIN')")
    public ResponseEntity<?> calificarEntrega(
            @PathVariable Long entregaId,
            @RequestBody CalificarRequest request
    ) {
        try {
            var entrega = calificacionService.calificarEntrega(
                    entregaId,
                    request.getNota(),
                    request.getComentario()
            );
            return ResponseEntity.ok(Map.of(
                    "message", "Entrega calificada exitosamente",
                    "entregaId", entrega.getId(),
                    "nota", entrega.getNota()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CalificarRequest {
        private BigDecimal nota;
        private String comentario;
    }
}
