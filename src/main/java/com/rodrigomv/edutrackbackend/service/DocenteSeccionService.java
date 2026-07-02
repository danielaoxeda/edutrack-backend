package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.dto.docenteSeccion.DocenteSeccionRequestDTO;
import com.rodrigomv.edutrackbackend.dto.docenteSeccion.DocenteSeccionResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.entity.Docente;
import com.rodrigomv.edutrackbackend.persistence.entity.DocenteSeccion;
import com.rodrigomv.edutrackbackend.persistence.entity.Seccion;
import com.rodrigomv.edutrackbackend.persistence.repository.DocenteRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.DocenteSeccionRepository;
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
public class DocenteSeccionService {
    
    private final DocenteSeccionRepository docenteSeccionRepository;
    private final DocenteRepository docenteRepository;
    private final SeccionRepository seccionRepository;
    
    public List<DocenteSeccionResponseDTO> findAll() {
        return docenteSeccionRepository.findAll().stream().map(this::toResponse).toList();
    }
    
    public Optional<DocenteSeccionResponseDTO> findById(Long id) {
        return docenteSeccionRepository.findById(id).map(this::toResponse);
    }
    
    public List<DocenteSeccionResponseDTO> findByDocente(Long docenteId) {
        return docenteSeccionRepository.findByDocenteId(docenteId).stream().map(this::toResponse).toList();
    }
    
    public List<DocenteSeccionResponseDTO> findBySeccion(Long seccionId) {
        return docenteSeccionRepository.findBySeccionId(seccionId).stream().map(this::toResponse).toList();
    }
    
    public Optional<DocenteSeccionResponseDTO> findByDocenteAndSeccion(Long docenteId, Long seccionId) {
        return docenteSeccionRepository.findByDocenteIdAndSeccionId(docenteId, seccionId).map(this::toResponse);
    }
    
    public DocenteSeccionResponseDTO save(DocenteSeccionRequestDTO request) {
        Docente docente = docenteRepository.findById(request.docenteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Docente no encontrado"));
        Seccion seccion = seccionRepository.findById(request.seccionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sección no encontrada"));

        if (docenteSeccionRepository.existsByDocenteIdAndSeccionId(docente.getId(), seccion.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El docente ya está asignado a esa sección");
        }

        DocenteSeccion docenteSeccion = new DocenteSeccion();
        docenteSeccion.setDocente(docente);
        docenteSeccion.setSeccion(seccion);
        return toResponse(docenteSeccionRepository.save(docenteSeccion));
    }
    
    public void delete(Long id) {
        docenteSeccionRepository.deleteById(id);
    }
    
    public boolean existsByDocenteAndSeccion(Long docenteId, Long seccionId) {
        return docenteSeccionRepository.existsByDocenteIdAndSeccionId(docenteId, seccionId);
    }

    private DocenteSeccionResponseDTO toResponse(DocenteSeccion docenteSeccion) {
        return new DocenteSeccionResponseDTO(
                docenteSeccion.getId(),
                docenteSeccion.getDocente() != null ? docenteSeccion.getDocente().getId() : null,
                docenteSeccion.getDocente() != null ? docenteSeccion.getDocente().getCodigoDocente() : null,
                docenteSeccion.getSeccion() != null ? docenteSeccion.getSeccion().getId() : null,
                docenteSeccion.getSeccion() != null ? docenteSeccion.getSeccion().getNombre() : null
        );
    }
}