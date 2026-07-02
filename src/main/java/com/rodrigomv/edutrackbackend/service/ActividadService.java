package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.dto.actividad.ActividadRequestDTO;
import com.rodrigomv.edutrackbackend.dto.actividad.ActividadResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.entity.Actividad;
import com.rodrigomv.edutrackbackend.persistence.entity.CriterioEvaluacion;
import com.rodrigomv.edutrackbackend.persistence.entity.SemanaAcademica;
import com.rodrigomv.edutrackbackend.persistence.enums.ActividadTipo;
import com.rodrigomv.edutrackbackend.persistence.repository.ActividadRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.CriterioEvaluacionRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.SemanaAcademicaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ActividadService {
    
    private final ActividadRepository actividadRepository;
    private final SemanaAcademicaRepository semanaAcademicaRepository;
    private final CriterioEvaluacionRepository criterioEvaluacionRepository;
    
    public List<ActividadResponseDTO> findAll() {
        return actividadRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }
    
    public Optional<ActividadResponseDTO> findById(Long id) {
        return actividadRepository.findById(id).map(this::toResponse);
    }
    
    public List<ActividadResponseDTO> findBySemana(Long semanaId) {
        return actividadRepository.findBySemanaAcademicaId(semanaId).stream()
                .map(this::toResponse)
                .toList();
    }
    
    public List<ActividadResponseDTO> findByTipo(ActividadTipo tipo) {
        return actividadRepository.findByTipo(tipo).stream()
                .map(this::toResponse)
                .toList();
    }
    
    public List<ActividadResponseDTO> findVisibles() {
        return actividadRepository.findByVisibleTrue().stream()
                .map(this::toResponse)
                .toList();
    }
    
    public List<ActividadResponseDTO> findByFechaLimiteBetween(LocalDateTime inicio, LocalDateTime fin) {
        return actividadRepository.findByFechaLimiteBetween(inicio, fin).stream()
                .map(this::toResponse)
                .toList();
    }
    
    public ActividadResponseDTO save(ActividadRequestDTO request) {
        Actividad actividad = new Actividad();
        applyRequest(actividad, request);
        return toResponse(actividadRepository.save(actividad));
    }
    
    public ActividadResponseDTO update(Long id, ActividadRequestDTO request) {
        Actividad actividad = actividadRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Actividad no encontrada"));
        applyRequest(actividad, request);
        return toResponse(actividadRepository.save(actividad));
    }
    
    public void delete(Long id) {
        actividadRepository.deleteById(id);
    }

    private void applyRequest(Actividad actividad, ActividadRequestDTO request) {
        SemanaAcademica semanaAcademica = semanaAcademicaRepository.findById(request.semanaAcademicaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Semana académica no encontrada"));
        actividad.setSemanaAcademica(semanaAcademica);

        if (request.criterioEvaluacionId() != null) {
            CriterioEvaluacion criterioEvaluacion = criterioEvaluacionRepository.findById(request.criterioEvaluacionId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Criterio de evaluación no encontrado"));
            actividad.setCriterioEvaluacion(criterioEvaluacion);
        } else if (actividad.getCriterioEvaluacion() != null && request.criterioEvaluacionId() == null) {
            actividad.setCriterioEvaluacion(null);
        }

        actividad.setTitulo(request.titulo());
        actividad.setDescripcion(request.descripcion());
        actividad.setTipo(request.tipo());
        actividad.setFechaLimite(request.fechaLimite());
        actividad.setCalificada(request.calificada() != null ? request.calificada() : (actividad.getCalificada() != null ? actividad.getCalificada() : false));
        actividad.setNotaMaxima(request.notaMaxima());
        actividad.setVisible(request.visible() != null ? request.visible() : (actividad.getVisible() != null ? actividad.getVisible() : true));
    }

    private ActividadResponseDTO toResponse(Actividad actividad) {
        return new ActividadResponseDTO(
                actividad.getId(),
                actividad.getSemanaAcademica() != null ? actividad.getSemanaAcademica().getId() : null,
                actividad.getSemanaAcademica() != null ? actividad.getSemanaAcademica().getTitulo() : null,
                actividad.getCriterioEvaluacion() != null ? actividad.getCriterioEvaluacion().getId() : null,
                actividad.getCriterioEvaluacion() != null ? actividad.getCriterioEvaluacion().getNombre() : null,
                actividad.getTitulo(),
                actividad.getDescripcion(),
                actividad.getTipo(),
                actividad.getFechaLimite(),
                actividad.getCalificada(),
                actividad.getNotaMaxima(),
                actividad.getVisible()
        );
    }
}