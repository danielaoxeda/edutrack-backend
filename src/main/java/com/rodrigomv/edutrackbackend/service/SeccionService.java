package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.dto.seccion.SeccionRequestDTO;
import com.rodrigomv.edutrackbackend.dto.seccion.SeccionResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.entity.Curso;
import com.rodrigomv.edutrackbackend.persistence.entity.PeriodoAcademico;
import com.rodrigomv.edutrackbackend.persistence.entity.Seccion;
import com.rodrigomv.edutrackbackend.persistence.repository.CursoRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.PeriodoAcademicoRepository;
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
public class SeccionService {
    
    private final SeccionRepository seccionRepository;
    private final CursoRepository cursoRepository;
    private final PeriodoAcademicoRepository periodoAcademicoRepository;
    
    public List<SeccionResponseDTO> findAll() {
        return seccionRepository.findAll().stream().map(this::toResponse).toList();
    }
    
    public Optional<SeccionResponseDTO> findById(Long id) {
        return seccionRepository.findById(id).map(this::toResponse);
    }
    
    public List<SeccionResponseDTO> findByPeriodo(Long periodoId) {
        return seccionRepository.findByPeriodoAcademicoId(periodoId).stream().map(this::toResponse).toList();
    }
    
    public List<SeccionResponseDTO> findByCurso(Long cursoId) {
        return seccionRepository.findByCursoId(cursoId).stream().map(this::toResponse).toList();
    }
    
    public SeccionResponseDTO save(SeccionRequestDTO request) {
        Seccion seccion = new Seccion();
        applyRequest(seccion, request);
        return toResponse(seccionRepository.save(seccion));
    }
    
    public SeccionResponseDTO update(Long id, SeccionRequestDTO request) {
        Seccion seccion = seccionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sección no encontrada"));
        applyRequest(seccion, request);
        return toResponse(seccionRepository.save(seccion));
    }
    
    public void delete(Long id) {
        seccionRepository.deleteById(id);
    }

    private void applyRequest(Seccion seccion, SeccionRequestDTO request) {
        Curso curso = cursoRepository.findById(request.cursoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado"));
        PeriodoAcademico periodo = periodoAcademicoRepository.findById(request.periodoAcademicoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Periodo académico no encontrado"));
        seccion.setCurso(curso);
        seccion.setPeriodoAcademico(periodo);
        seccion.setNombre(request.nombre());
        seccion.setCapacidad(request.capacidad());
    }

    private SeccionResponseDTO toResponse(Seccion seccion) {
        return new SeccionResponseDTO(
                seccion.getId(),
                seccion.getCurso() != null ? seccion.getCurso().getId() : null,
                seccion.getCurso() != null ? seccion.getCurso().getCodigo() : null,
                seccion.getPeriodoAcademico() != null ? seccion.getPeriodoAcademico().getId() : null,
                seccion.getPeriodoAcademico() != null ? seccion.getPeriodoAcademico().getNombre() : null,
                seccion.getNombre(),
                seccion.getCapacidad()
        );
    }
}