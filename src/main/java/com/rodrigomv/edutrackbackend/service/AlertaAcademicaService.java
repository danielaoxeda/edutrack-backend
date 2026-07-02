package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.dto.alertaAcademica.AlertaAcademicaRequestDTO;
import com.rodrigomv.edutrackbackend.dto.alertaAcademica.AlertaAcademicaResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.entity.AlertaAcademica;
import com.rodrigomv.edutrackbackend.persistence.entity.Matricula;
import com.rodrigomv.edutrackbackend.persistence.enums.AlertaTipo;
import com.rodrigomv.edutrackbackend.persistence.repository.AlertaAcademicaRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.MatriculaRepository;
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
public class AlertaAcademicaService {
    
    private final AlertaAcademicaRepository alertaRepository;
    private final MatriculaRepository matriculaRepository;
    
    public List<AlertaAcademicaResponseDTO> findAll() {
        return alertaRepository.findAll().stream().map(this::toResponse).toList();
    }
    
    public Optional<AlertaAcademicaResponseDTO> findById(Long id) {
        return alertaRepository.findById(id).map(this::toResponse);
    }
    
    public List<AlertaAcademicaResponseDTO> findByMatricula(Long matriculaId) {
        return alertaRepository.findByMatriculaId(matriculaId).stream().map(this::toResponse).toList();
    }
    
    public List<AlertaAcademicaResponseDTO> findByTipo(AlertaTipo tipo) {
        return alertaRepository.findByTipo(tipo).stream().map(this::toResponse).toList();
    }
    
    public AlertaAcademicaResponseDTO save(AlertaAcademicaRequestDTO request) {
        AlertaAcademica alerta = new AlertaAcademica();
        applyRequest(alerta, request);
        return toResponse(alertaRepository.save(alerta));
    }
    
    public AlertaAcademicaResponseDTO update(Long id, AlertaAcademicaRequestDTO request) {
        AlertaAcademica alerta = alertaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alerta no encontrada"));
        applyRequest(alerta, request);
        return toResponse(alertaRepository.save(alerta));
    }
    
    public void delete(Long id) {
        alertaRepository.deleteById(id);
    }

    private void applyRequest(AlertaAcademica alerta, AlertaAcademicaRequestDTO request) {
        Matricula matricula = matriculaRepository.findById(request.matriculaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula no encontrada"));
        alerta.setMatricula(matricula);
        alerta.setTipo(request.tipo());
        alerta.setDescripcion(request.descripcion());
        if (alerta.getFechaAlerta() == null) {
            alerta.setFechaAlerta(LocalDateTime.now());
        }
    }

    private AlertaAcademicaResponseDTO toResponse(AlertaAcademica alerta) {
        return new AlertaAcademicaResponseDTO(
                alerta.getId(),
                alerta.getMatricula() != null ? alerta.getMatricula().getId() : null,
                alerta.getMatricula() != null && alerta.getMatricula().getEstudiante() != null ? alerta.getMatricula().getEstudiante().getCodigoEstudiante() : null,
                alerta.getMatricula() != null && alerta.getMatricula().getSeccion() != null ? alerta.getMatricula().getSeccion().getNombre() : null,
                alerta.getTipo(),
                alerta.getDescripcion(),
                alerta.getFechaAlerta()
        );
    }
}