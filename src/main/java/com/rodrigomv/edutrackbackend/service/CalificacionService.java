package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.persistence.entity.*;
import com.rodrigomv.edutrackbackend.persistence.enums.EntregaEstado;
import com.rodrigomv.edutrackbackend.persistence.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CalificacionService {

    private final EntregaRepository entregaRepository;
    private final MatriculaRepository matriculaRepository;
    private final CriterioEvaluacionRepository criterioRepository;
    private final ActividadRepository actividadRepository;

    /**
     * Califica una entrega
     */
    @Transactional
    public Entrega calificarEntrega(Long entregaId, BigDecimal nota, String comentario) {
        Entrega entrega = entregaRepository.findById(entregaId)
                .orElseThrow(() -> new RuntimeException("Entrega no encontrada"));

        if (nota.compareTo(BigDecimal.ZERO) < 0 || nota.compareTo(entrega.getActividad().getNotaMaxima()) > 0) {
            throw new RuntimeException("La nota debe estar entre 0 y " + entrega.getActividad().getNotaMaxima());
        }

        entrega.setNota(nota);
        entrega.setComentarioDocente(comentario);
        entrega.setEstado(EntregaEstado.CALIFICADO);

        return entregaRepository.save(entrega);
    }

    /**
     * Obtiene boleta de notas de un estudiante en una sección
     */
    public Map<String, Object> obtenerBoletaEstudiante(Long estudianteId, Long seccionId) {
        List<Matricula> matriculas = matriculaRepository.findByEstudianteIdAndSeccionId(estudianteId, seccionId);

        if (matriculas.isEmpty()) {
            throw new RuntimeException("Matrícula no encontrada");
        }

        Matricula matricula = matriculas.get(0);
        Seccion seccion = matricula.getSeccion();

        // Obtener criterios de evaluación
        List<CriterioEvaluacion> criterios = criterioRepository.findBySeccionId(seccionId);

        // Obtener actividades calificadas del estudiante
        List<Entrega> entregas = entregaRepository.findByMatriculaId(matricula.getId());
        Map<Long, List<Entrega>> entregasPorActividad = new HashMap<>();
        for (Entrega e : entregas) {
            if (e.getNota() != null) {
                entregasPorActividad.computeIfAbsent(e.getActividad().getId(), k -> new ArrayList<>()).add(e);
            }
        }

        // Construir boleta
        List<Map<String, Object>> criteriosData = new ArrayList<>();
        BigDecimal promedioFinal = BigDecimal.ZERO;
        BigDecimal totalPorcentaje = BigDecimal.ZERO;

        for (CriterioEvaluacion criterio : criterios) {
            Map<String, Object> criterioData = new HashMap<>();
            criterioData.put("id", criterio.getId());
            criterioData.put("nombre", criterio.getNombre());
            criterioData.put("porcentaje", criterio.getPorcentaje());

            // Obtener actividades de este criterio
            List<Actividad> actividadesCriterio = actividadRepository.findByCriterioEvaluacionId(criterio.getId());

            List<Map<String, Object>> actividadesData = new ArrayList<>();
            BigDecimal sumaPonderada = BigDecimal.ZERO;
            BigDecimal sumaMaxima = BigDecimal.ZERO;

            for (Actividad actividad : actividadesCriterio) {
                Map<String, Object> actividadData = new HashMap<>();
                actividadData.put("id", actividad.getId());
                actividadData.put("titulo", actividad.getTitulo());
                actividadData.put("notaMaxima", actividad.getNotaMaxima());

                List<Entrega> entregasActividad = entregasPorActividad.get(actividad.getId());
                if (entregasActividad != null && !entregasActividad.isEmpty()) {
                    Entrega entrega = entregasActividad.get(0);
                    actividadData.put("nota", entrega.getNota());
                    actividadData.put("comentario", entrega.getComentarioDocente());
                    sumaPonderada = sumaPonderada.add(entrega.getNota());
                    sumaMaxima = sumaMaxima.add(actividad.getNotaMaxima());
                } else {
                    actividadData.put("nota", null);
                    actividadData.put("comentario", null);
                }

                actividadesData.add(actividadData);
            }

            criterioData.put("actividades", actividadesData);

            // Calcular promedio del criterio
            if (sumaMaxima.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal promedioCriterio = sumaPonderada.divide(sumaMaxima, 4, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal("20")).setScale(2, RoundingMode.HALF_UP);
                criterioData.put("promedioCriterio", promedioCriterio);

                // Ponderar al promedio final
                BigDecimal contribucion = promedioCriterio.multiply(criterio.getPorcentaje())
                        .divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
                promedioFinal = promedioFinal.add(contribucion);
                totalPorcentaje = totalPorcentaje.add(criterio.getPorcentaje());
            }

            criteriosData.add(criterioData);
        }

        // Ajustar promedio si no está 100%
        if (totalPorcentaje.compareTo(BigDecimal.ZERO) > 0 && totalPorcentaje.compareTo(new BigDecimal("100")) < 0) {
            promedioFinal = promedioFinal.multiply(new BigDecimal("100")).divide(totalPorcentaje, 2, RoundingMode.HALF_UP);
        }

        Map<String, Object> boleta = new HashMap<>();
        boleta.put("estudiante", Map.of(
                "id", matricula.getEstudiante().getId(),
                "nombre", matricula.getEstudiante().getUsuario().getNombres() + " " + matricula.getEstudiante().getUsuario().getApellidos(),
                "codigo", matricula.getEstudiante().getCodigoEstudiante()
        ));
        boleta.put("seccion", Map.of(
                "id", seccion.getId(),
                "nombre", seccion.getNombre(),
                "curso", seccion.getCurso().getNombre()
        ));
        boleta.put("criterios", criteriosData);
        boleta.put("promedioFinal", promedioFinal.setScale(2, RoundingMode.HALF_UP));
        boleta.put("estado", matricula.getEstado());

        return boleta;
    }

    /**
     * Obtiene resumen de calificaciones de una sección (para docente)
     */
    public Map<String, Object> obtenerResumenSeccion(Long seccionId) {
        Seccion seccion = null;
        for (Matricula m : matriculaRepository.findBySeccionId(seccionId)) {
            seccion = m.getSeccion();
            break;
        }

        if (seccion == null) {
            throw new RuntimeException("Sección no encontrada");
        }

        List<Matricula> matriculas = matriculaRepository.findBySeccionId(seccionId);
        List<CriterioEvaluacion> criterios = criterioRepository.findBySeccionId(seccionId);

        List<Map<String, Object>> estudiantesData = new ArrayList<>();
        BigDecimal promedioSeccion = BigDecimal.ZERO;
        int countConPromedio = 0;

        for (Matricula matricula : matriculas) {
            try {
                Map<String, Object> boleta = obtenerBoletaEstudiante(
                        matricula.getEstudiante().getId(), seccionId);
                BigDecimal promedio = (BigDecimal) boleta.get("promedioFinal");

                Map<String, Object> estudianteData = new HashMap<>();
                estudianteData.put("matriculaId", matricula.getId());
                estudianteData.put("estudiante", boleta.get("estudiante"));
                estudianteData.put("promedio", promedio);
                estudianteData.put("estado", matricula.getEstado());

                // Contar entregas
                List<Entrega> entregas = entregaRepository.findByMatriculaId(matricula.getId());
                long entregasCalificadas = entregas.stream().filter(e -> e.getNota() != null).count();
                estudianteData.put("entregasCalificadas", entregasCalificadas);
                estudianteData.put("totalEntregas", entregas.size());

                estudiantesData.add(estudianteData);

                if (promedio.compareTo(BigDecimal.ZERO) > 0) {
                    promedioSeccion = promedioSeccion.add(promedio);
                    countConPromedio++;
                }
            } catch (Exception e) {
                // Skip students with no data
            }
        }

        // Ordenar por promedio descendente
        estudiantesData.sort((a, b) -> {
            BigDecimal pA = (BigDecimal) a.get("promedio");
            BigDecimal pB = (BigDecimal) b.get("promedio");
            if (pA == null) pA = BigDecimal.ZERO;
            if (pB == null) pB = BigDecimal.ZERO;
            return pB.compareTo(pA);
        });

        Map<String, Object> resumen = new HashMap<>();
        resumen.put("seccion", Map.of(
                "id", seccion.getId(),
                "nombre", seccion.getNombre(),
                "curso", seccion.getCurso().getNombre()
        ));
        resumen.put("criterios", criterios.stream().map(c -> Map.of(
                "id", c.getId(),
                "nombre", c.getNombre(),
                "porcentaje", c.getPorcentaje()
        )).toList());
        resumen.put("totalEstudiantes", matriculas.size());
        resumen.put("promedioSeccion", countConPromedio > 0 ?
                promedioSeccion.divide(new BigDecimal(countConPromedio), 2, RoundingMode.HALF_UP) :
                BigDecimal.ZERO);
        resumen.put("estudiantes", estudiantesData);

        return resumen;
    }

    /**
     * Estadísticas generales de una sección
     */
    public Map<String, Object> obtenerEstadisticasSeccion(Long seccionId) {
        List<Matricula> matriculas = matriculaRepository.findBySeccionId(seccionId);
        List<BigDecimal> promedios = new ArrayList<>();

        for (Matricula matricula : matriculas) {
            try {
                Map<String, Object> boleta = obtenerBoletaEstudiante(
                        matricula.getEstudiante().getId(), seccionId);
                BigDecimal promedio = (BigDecimal) boleta.get("promedioFinal");
                if (promedio != null && promedio.compareTo(BigDecimal.ZERO) > 0) {
                    promedios.add(promedio);
                }
            } catch (Exception e) {
                // Skip
            }
        }

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalEstudiantes", matriculas.size());
        stats.put("estudiantesCalificados", promedios.size());

        if (!promedios.isEmpty()) {
            Collections.sort(promedios);
            BigDecimal suma = promedios.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
            stats.put("promedio", suma.divide(new BigDecimal(promedios.size()), 2, RoundingMode.HALF_UP));
            stats.put("maxima", promedios.get(promedios.size() - 1));
            stats.put("minima", promedios.get(0));
            stats.put("mediana", promedios.get(promedios.size() / 2));

            // Distribución
            long aprobo = promedios.stream().filter(p -> p.compareTo(new BigDecimal("10.5")) >= 0).count();
            long desaprobo = promedios.size() - aprobo;
            stats.put("aprobados", aprobo);
            stats.put("desaprobados", desaprobo);
            stats.put("porcentajeAprobacion", promedios.size() > 0 ?
                    new BigDecimal(aprobo).multiply(new BigDecimal("100"))
                            .divide(new BigDecimal(promedios.size()), 1, RoundingMode.HALF_UP) :
                    BigDecimal.ZERO);
        } else {
            stats.put("promedio", BigDecimal.ZERO);
            stats.put("maxima", BigDecimal.ZERO);
            stats.put("minima", BigDecimal.ZERO);
            stats.put("mediana", BigDecimal.ZERO);
            stats.put("aprobados", 0L);
            stats.put("desaprobados", 0L);
            stats.put("porcentajeAprobacion", BigDecimal.ZERO);
        }

        return stats;
    }
}
