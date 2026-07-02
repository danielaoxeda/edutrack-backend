package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.dto.semanaAcademica.SemanaAcademicaRequestDTO;
import com.rodrigomv.edutrackbackend.dto.semanaAcademica.SemanaAcademicaResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.entity.SemanaAcademica;
import com.rodrigomv.edutrackbackend.persistence.entity.Seccion;
import com.rodrigomv.edutrackbackend.persistence.repository.SemanaAcademicaRepository;
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
public class SemanaAcademicaService {
    
    private final SemanaAcademicaRepository semanaRepository;
    private final SeccionRepository seccionRepository;
    
    public List<SemanaAcademicaResponseDTO> findAll() {
        return semanaRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }
    
    public Optional<SemanaAcademicaResponseDTO> findById(Long id) {
        return semanaRepository.findById(id).map(this::toResponse);
    }
    
    public List<SemanaAcademicaResponseDTO> findBySeccion(Long seccionId) {
        return semanaRepository.findBySeccionIdOrderByNumeroSemanaAsc(seccionId).stream()
                .map(this::toResponse)
                .toList();
    }
    
    public SemanaAcademicaResponseDTO save(SemanaAcademicaRequestDTO request) {
        SemanaAcademica semana = new SemanaAcademica();
        applyRequest(semana, request);
        return toResponse(semanaRepository.save(semana));
    }
    
    public SemanaAcademicaResponseDTO update(Long id, SemanaAcademicaRequestDTO request) {
        SemanaAcademica semana = semanaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Semana académica no encontrada"));
        applyRequest(semana, request);
        return toResponse(semanaRepository.save(semana));
    }
    
    public void delete(Long id) {
        semanaRepository.deleteById(id);
    }

    private void applyRequest(SemanaAcademica semana, SemanaAcademicaRequestDTO request) {
        Seccion seccion = seccionRepository.findById(request.seccionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sección no encontrada"));

        semana.setSeccion(seccion);
        semana.setNumeroSemana(request.numeroSemana());
        semana.setTitulo(request.titulo());
    }

    private SemanaAcademicaResponseDTO toResponse(SemanaAcademica semana) {
        return new SemanaAcademicaResponseDTO(
                semana.getId(),
                semana.getSeccion() != null ? semana.getSeccion().getId() : null,
                semana.getSeccion() != null ? semana.getSeccion().getNombre() : null,
                semana.getNumeroSemana(),
                semana.getTitulo()
        );
    }
}