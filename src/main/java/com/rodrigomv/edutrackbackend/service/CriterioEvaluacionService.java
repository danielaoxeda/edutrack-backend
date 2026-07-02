package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.dto.criterioEvaluacion.CriterioEvaluacionRequestDTO;
import com.rodrigomv.edutrackbackend.dto.criterioEvaluacion.CriterioEvaluacionResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.entity.CriterioEvaluacion;
import com.rodrigomv.edutrackbackend.persistence.entity.Seccion;
import com.rodrigomv.edutrackbackend.persistence.repository.CriterioEvaluacionRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.SeccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CriterioEvaluacionService {
    
    private final CriterioEvaluacionRepository criterioRepository;
    private final SeccionRepository seccionRepository;
    
    public List<CriterioEvaluacionResponseDTO> findAll() {
        return criterioRepository.findAll().stream().map(this::toResponse).toList();
    }
    
    public Optional<CriterioEvaluacionResponseDTO> findById(Long id) {
        return criterioRepository.findById(id).map(this::toResponse);
    }
    
    public List<CriterioEvaluacionResponseDTO> findBySeccion(Long seccionId) {
        return criterioRepository.findBySeccionId(seccionId).stream().map(this::toResponse).toList();
    }
    
    public CriterioEvaluacionResponseDTO save(CriterioEvaluacionRequestDTO request) {
        CriterioEvaluacion criterio = new CriterioEvaluacion();
        applyRequest(criterio, request);
        return toResponse(criterioRepository.save(criterio));
    }
    
    public CriterioEvaluacionResponseDTO update(Long id, CriterioEvaluacionRequestDTO request) {
        CriterioEvaluacion criterio = criterioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Criterio de evaluación no encontrado"));
        applyRequest(criterio, request);
        return toResponse(criterioRepository.save(criterio));
    }
    
    public void delete(Long id) {
        criterioRepository.deleteById(id);
    }

    private void applyRequest(CriterioEvaluacion criterio, CriterioEvaluacionRequestDTO request) {
        Seccion seccion = seccionRepository.findById(request.seccionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sección no encontrada"));
        criterio.setSeccion(seccion);
        criterio.setNombre(request.nombre());
        criterio.setPorcentaje(request.porcentaje());
    }

    private CriterioEvaluacionResponseDTO toResponse(CriterioEvaluacion criterio) {
        return new CriterioEvaluacionResponseDTO(
                criterio.getId(),
                criterio.getSeccion() != null ? criterio.getSeccion().getId() : null,
                criterio.getSeccion() != null ? criterio.getSeccion().getNombre() : null,
                criterio.getNombre(),
                criterio.getPorcentaje()
        );
    }
}