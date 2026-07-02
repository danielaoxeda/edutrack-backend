package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.dto.sesionClase.SesionClaseRequestDTO;
import com.rodrigomv.edutrackbackend.dto.sesionClase.SesionClaseResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.entity.SesionClase;
import com.rodrigomv.edutrackbackend.persistence.entity.SemanaAcademica;
import com.rodrigomv.edutrackbackend.persistence.repository.SemanaAcademicaRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.SesionClaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SesionClaseService {
    
    private final SesionClaseRepository sesionClaseRepository;
    private final SemanaAcademicaRepository semanaAcademicaRepository;
    
    public List<SesionClaseResponseDTO> findAll() {
        return sesionClaseRepository.findAll().stream().map(this::toResponse).toList();
    }
    
    public Optional<SesionClaseResponseDTO> findById(Long id) {
        return sesionClaseRepository.findById(id).map(this::toResponse);
    }
    
    public List<SesionClaseResponseDTO> findBySemana(Long semanaId) {
        return sesionClaseRepository.findBySemanaAcademicaId(semanaId).stream().map(this::toResponse).toList();
    }
    
    public List<SesionClaseResponseDTO> findByFecha(LocalDate fecha) {
        return sesionClaseRepository.findByFecha(fecha).stream().map(this::toResponse).toList();
    }
    
    public List<SesionClaseResponseDTO> findByFechaBetween(LocalDate inicio, LocalDate fin) {
        return sesionClaseRepository.findByFechaBetween(inicio, fin).stream().map(this::toResponse).toList();
    }
    
    public SesionClaseResponseDTO save(SesionClaseRequestDTO request) {
        SesionClase sesion = new SesionClase();
        applyRequest(sesion, request);
        return toResponse(sesionClaseRepository.save(sesion));
    }
    
    public SesionClaseResponseDTO update(Long id, SesionClaseRequestDTO request) {
        SesionClase sesion = sesionClaseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sesión de clase no encontrada"));
        applyRequest(sesion, request);
        return toResponse(sesionClaseRepository.save(sesion));
    }
    
    public void delete(Long id) {
        sesionClaseRepository.deleteById(id);
    }

    private void applyRequest(SesionClase sesion, SesionClaseRequestDTO request) {
        SemanaAcademica semana = semanaAcademicaRepository.findById(request.semanaAcademicaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Semana académica no encontrada"));
        sesion.setSemanaAcademica(semana);
        sesion.setTema(request.tema());
        sesion.setFecha(request.fecha());
    }

    private SesionClaseResponseDTO toResponse(SesionClase sesion) {
        return new SesionClaseResponseDTO(
                sesion.getId(),
                sesion.getSemanaAcademica() != null ? sesion.getSemanaAcademica().getId() : null,
                sesion.getSemanaAcademica() != null ? sesion.getSemanaAcademica().getTitulo() : null,
                sesion.getTema(),
                sesion.getFecha()
        );
    }
}