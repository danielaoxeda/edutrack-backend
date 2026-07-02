package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.dto.periodoAcademico.PeriodoAcademicoRequestDTO;
import com.rodrigomv.edutrackbackend.dto.periodoAcademico.PeriodoAcademicoResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.entity.PeriodoAcademico;
import com.rodrigomv.edutrackbackend.persistence.enums.PeriodoEstado;
import com.rodrigomv.edutrackbackend.persistence.repository.PeriodoAcademicoRepository;
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
public class PeriodoAcademicoService {
    
    private final PeriodoAcademicoRepository periodoAcademicoRepository;
    
    public List<PeriodoAcademicoResponseDTO> findAll() {
        return periodoAcademicoRepository.findAll().stream().map(this::toResponse).toList();
    }
    
    public Optional<PeriodoAcademicoResponseDTO> findById(Long id) {
        return periodoAcademicoRepository.findById(id).map(this::toResponse);
    }
    
    public Optional<PeriodoAcademicoResponseDTO> findByNombre(String nombre) {
        return periodoAcademicoRepository.findByNombre(nombre).map(this::toResponse);
    }
    
    public List<PeriodoAcademicoResponseDTO> findByEstado(PeriodoEstado estado) {
        return periodoAcademicoRepository.findByEstado(estado).stream().map(this::toResponse).toList();
    }
    
    public Optional<PeriodoAcademicoResponseDTO> findActivo() {
        return periodoAcademicoRepository.findActivo().map(this::toResponse);
    }
    
    public PeriodoAcademicoResponseDTO save(PeriodoAcademicoRequestDTO request) {
        PeriodoAcademico periodo = new PeriodoAcademico();
        applyRequest(periodo, request);
        return toResponse(periodoAcademicoRepository.save(periodo));
    }
    
    public PeriodoAcademicoResponseDTO update(Long id, PeriodoAcademicoRequestDTO request) {
        PeriodoAcademico periodo = periodoAcademicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Periodo académico no encontrado"));
        applyRequest(periodo, request);
        return toResponse(periodoAcademicoRepository.save(periodo));
    }
    
    public void delete(Long id) {
        periodoAcademicoRepository.deleteById(id);
    }

    private void applyRequest(PeriodoAcademico periodo, PeriodoAcademicoRequestDTO request) {
        periodo.setNombre(request.nombre());
        periodo.setFechaInicio(request.fechaInicio());
        periodo.setFechaFin(request.fechaFin());
        periodo.setNumeroSemanas(request.numeroSemanas());
        periodo.setEstado(request.estado() != null ? request.estado() : (periodo.getEstado() != null ? periodo.getEstado() : PeriodoEstado.ACTIVO));
    }

    private PeriodoAcademicoResponseDTO toResponse(PeriodoAcademico periodo) {
        return new PeriodoAcademicoResponseDTO(periodo.getId(), periodo.getNombre(), periodo.getFechaInicio(), periodo.getFechaFin(), periodo.getNumeroSemanas(), periodo.getEstado());
    }
}