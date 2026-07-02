package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.dto.entrega.EntregaRequestDTO;
import com.rodrigomv.edutrackbackend.dto.entrega.EntregaResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.entity.Actividad;
import com.rodrigomv.edutrackbackend.persistence.entity.Entrega;
import com.rodrigomv.edutrackbackend.persistence.entity.Matricula;
import com.rodrigomv.edutrackbackend.persistence.enums.EntregaEstado;
import com.rodrigomv.edutrackbackend.persistence.repository.ActividadRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.EntregaRepository;
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
public class EntregaService {
    
    private final EntregaRepository entregaRepository;
    private final ActividadRepository actividadRepository;
    private final MatriculaRepository matriculaRepository;
    
    public List<EntregaResponseDTO> findAll() {
        return entregaRepository.findAll().stream().map(this::toResponse).toList();
    }
    
    public Optional<EntregaResponseDTO> findById(Long id) {
        return entregaRepository.findById(id).map(this::toResponse);
    }
    
    public List<EntregaResponseDTO> findByActividad(Long actividadId) {
        return entregaRepository.findByActividadId(actividadId).stream().map(this::toResponse).toList();
    }
    
    public List<EntregaResponseDTO> findByMatricula(Long matriculaId) {
        return entregaRepository.findByMatriculaId(matriculaId).stream().map(this::toResponse).toList();
    }
    
    public List<EntregaResponseDTO> findByEstado(EntregaEstado estado) {
        return entregaRepository.findByEstado(estado).stream().map(this::toResponse).toList();
    }
    
    public Optional<EntregaResponseDTO> findByActividadAndMatricula(Long actividadId, Long matriculaId) {
        return entregaRepository.findByActividadIdAndMatriculaId(actividadId, matriculaId).map(this::toResponse);
    }

    public Optional<Entrega> findEntityById(Long id) {
        return entregaRepository.findById(id);
    }
    
    public EntregaResponseDTO save(EntregaRequestDTO request) {
        Entrega entrega = new Entrega();
        applyRequest(entrega, request);
        return toResponse(entregaRepository.save(entrega));
    }
    
    public EntregaResponseDTO update(Long id, EntregaRequestDTO request) {
        Entrega entrega = entregaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entrega no encontrada"));
        applyRequest(entrega, request);
        return toResponse(entregaRepository.save(entrega));
    }

    public Entrega updateEntity(Long id, Entrega entrega) {
        Entrega current = entregaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entrega no encontrada"));
        current.setComentarioAlumno(entrega.getComentarioAlumno());
        current.setArchivoUrl(entrega.getArchivoUrl());
        current.setEstado(entrega.getEstado());
        current.setNota(entrega.getNota());
        current.setComentarioDocente(entrega.getComentarioDocente());
        if (entrega.getFechaEntrega() != null) {
            current.setFechaEntrega(entrega.getFechaEntrega());
        }
        return entregaRepository.save(current);
    }
    
    public void delete(Long id) {
        entregaRepository.deleteById(id);
    }
    
    public boolean existsByActividadAndMatricula(Long actividadId, Long matriculaId) {
        return entregaRepository.existsByActividadIdAndMatriculaId(actividadId, matriculaId);
    }

    private void applyRequest(Entrega entrega, EntregaRequestDTO request) {
        Actividad actividad = actividadRepository.findById(request.actividadId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Actividad no encontrada"));
        Matricula matricula = matriculaRepository.findById(request.matriculaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula no encontrada"));
        entrega.setActividad(actividad);
        entrega.setMatricula(matricula);
        entrega.setComentarioAlumno(request.comentarioAlumno());
        entrega.setArchivoUrl(request.archivoUrl());
        entrega.setEstado(request.estado() != null ? request.estado() : (entrega.getEstado() != null ? entrega.getEstado() : EntregaEstado.ENTREGADO));
        entrega.setNota(request.nota());
        entrega.setComentarioDocente(request.comentarioDocente());
        if (entrega.getFechaEntrega() == null) {
            entrega.setFechaEntrega(LocalDateTime.now());
        }
    }

    private EntregaResponseDTO toResponse(Entrega entrega) {
        return new EntregaResponseDTO(
                entrega.getId(),
                entrega.getActividad() != null ? entrega.getActividad().getId() : null,
                entrega.getActividad() != null ? entrega.getActividad().getTitulo() : null,
                entrega.getMatricula() != null ? entrega.getMatricula().getId() : null,
                entrega.getMatricula() != null && entrega.getMatricula().getEstudiante() != null ? entrega.getMatricula().getEstudiante().getCodigoEstudiante() : null,
                entrega.getMatricula() != null && entrega.getMatricula().getSeccion() != null ? entrega.getMatricula().getSeccion().getNombre() : null,
                entrega.getComentarioAlumno(),
                entrega.getArchivoUrl(),
                entrega.getFechaEntrega(),
                entrega.getEstado(),
                entrega.getNota(),
                entrega.getComentarioDocente()
        );
    }
}