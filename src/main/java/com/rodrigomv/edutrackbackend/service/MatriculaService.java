package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.dto.matricula.MatriculaRequestDTO;
import com.rodrigomv.edutrackbackend.dto.matricula.MatriculaResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.entity.Estudiante;
import com.rodrigomv.edutrackbackend.persistence.entity.Matricula;
import com.rodrigomv.edutrackbackend.persistence.entity.Seccion;
import com.rodrigomv.edutrackbackend.persistence.enums.MatriculaEstado;
import com.rodrigomv.edutrackbackend.persistence.repository.EstudianteRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.MatriculaRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.SeccionRepository;
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
public class MatriculaService {
    
    private final MatriculaRepository matriculaRepository;
    private final EstudianteRepository estudianteRepository;
    private final SeccionRepository seccionRepository;
    
    public List<MatriculaResponseDTO> findAll() {
        return matriculaRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }
    
    public Optional<MatriculaResponseDTO> findById(Long id) {
        return matriculaRepository.findById(id).map(this::toResponse);
    }
    
    public List<MatriculaResponseDTO> findByEstudiante(Long estudianteId) {
        return matriculaRepository.findByEstudianteId(estudianteId).stream()
                .map(this::toResponse)
                .toList();
    }
    
    public List<MatriculaResponseDTO> findBySeccion(Long seccionId) {
        return matriculaRepository.findBySeccionId(seccionId).stream()
                .map(this::toResponse)
                .toList();
    }
    
    public List<MatriculaResponseDTO> findByEstado(MatriculaEstado estado) {
        return matriculaRepository.findByEstado(estado).stream()
                .map(this::toResponse)
                .toList();
    }
    
    public MatriculaResponseDTO save(MatriculaRequestDTO request) {
        Matricula matricula = new Matricula();
        applyRequest(matricula, request);
        return toResponse(matriculaRepository.save(matricula));
    }
    
    public MatriculaResponseDTO update(Long id, MatriculaRequestDTO request) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula no encontrada"));
        applyRequest(matricula, request);
        return toResponse(matriculaRepository.save(matricula));
    }
    
    public void delete(Long id) {
        matriculaRepository.deleteById(id);
    }
    
    public boolean existsByEstudianteAndSeccion(Long estudianteId, Long seccionId) {
        return matriculaRepository.existsByEstudianteIdAndSeccionId(estudianteId, seccionId);
    }

    private void applyRequest(Matricula matricula, MatriculaRequestDTO request) {
        Estudiante estudiante = estudianteRepository.findById(request.estudianteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudiante no encontrado"));
        Seccion seccion = seccionRepository.findById(request.seccionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sección no encontrada"));

        if (matricula.getId() == null && matriculaRepository.existsByEstudianteIdAndSeccionId(estudiante.getId(), seccion.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El estudiante ya está matriculado en esa sección");
        }

        matricula.setEstudiante(estudiante);
        matricula.setSeccion(seccion);
        matricula.setEstado(request.estado() != null ? request.estado() : (matricula.getEstado() != null ? matricula.getEstado() : MatriculaEstado.ACTIVO));
        if (matricula.getFechaMatricula() == null) {
            matricula.setFechaMatricula(LocalDateTime.now());
        }
    }

    private MatriculaResponseDTO toResponse(Matricula matricula) {
        return new MatriculaResponseDTO(
                matricula.getId(),
                matricula.getEstudiante() != null ? matricula.getEstudiante().getId() : null,
                matricula.getEstudiante() != null ? matricula.getEstudiante().getCodigoEstudiante() : null,
                matricula.getSeccion() != null ? matricula.getSeccion().getId() : null,
                matricula.getSeccion() != null ? matricula.getSeccion().getNombre() : null,
                matricula.getFechaMatricula(),
                matricula.getEstado()
        );
    }
}