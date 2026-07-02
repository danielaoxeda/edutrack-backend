package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.dto.asistencia.AsistenciaRequestDTO;
import com.rodrigomv.edutrackbackend.dto.asistencia.AsistenciaResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.entity.Asistencia;
import com.rodrigomv.edutrackbackend.persistence.entity.Matricula;
import com.rodrigomv.edutrackbackend.persistence.entity.SesionClase;
import com.rodrigomv.edutrackbackend.persistence.enums.AsistenciaEstado;
import com.rodrigomv.edutrackbackend.persistence.repository.MatriculaRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.AsistenciaRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.SesionClaseRepository;
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
public class AsistenciaService {
    
    private final AsistenciaRepository asistenciaRepository;
    private final SesionClaseRepository sesionClaseRepository;
    private final MatriculaRepository matriculaRepository;
    
    public List<AsistenciaResponseDTO> findAll() {
        return asistenciaRepository.findAll().stream().map(this::toResponse).toList();
    }
    
    public Optional<AsistenciaResponseDTO> findById(Long id) {
        return asistenciaRepository.findById(id).map(this::toResponse);
    }
    
    public List<AsistenciaResponseDTO> findBySesion(Long sesionId) {
        return asistenciaRepository.findBySesionClaseId(sesionId).stream().map(this::toResponse).toList();
    }
    
    public List<AsistenciaResponseDTO> findByMatricula(Long matriculaId) {
        return asistenciaRepository.findByMatriculaId(matriculaId).stream().map(this::toResponse).toList();
    }
    
    public List<AsistenciaResponseDTO> findByEstado(AsistenciaEstado estado) {
        return asistenciaRepository.findByEstado(estado).stream().map(this::toResponse).toList();
    }
    
    public Optional<AsistenciaResponseDTO> findBySesionAndMatricula(Long sesionId, Long matriculaId) {
        return asistenciaRepository.findBySesionClaseIdAndMatriculaId(sesionId, matriculaId).map(this::toResponse);
    }
    
    public AsistenciaResponseDTO save(AsistenciaRequestDTO request) {
        Asistencia asistencia = new Asistencia();
        applyRequest(asistencia, request);
        return toResponse(asistenciaRepository.save(asistencia));
    }
    
    public AsistenciaResponseDTO update(Long id, AsistenciaRequestDTO request) {
        Asistencia asistencia = asistenciaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Asistencia no encontrada"));
        applyRequest(asistencia, request);
        return toResponse(asistenciaRepository.save(asistencia));
    }
    
    public void delete(Long id) {
        asistenciaRepository.deleteById(id);
    }

    private void applyRequest(Asistencia asistencia, AsistenciaRequestDTO request) {
        SesionClase sesion = sesionClaseRepository.findById(request.sesionClaseId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sesión de clase no encontrada"));
        Matricula matricula = matriculaRepository.findById(request.matriculaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula no encontrada"));
        asistencia.setSesionClase(sesion);
        asistencia.setMatricula(matricula);
        asistencia.setEstado(request.estado());
        asistencia.setJustificada(request.justificada());
    }

    private AsistenciaResponseDTO toResponse(Asistencia asistencia) {
        return new AsistenciaResponseDTO(
                asistencia.getId(),
                asistencia.getSesionClase() != null ? asistencia.getSesionClase().getId() : null,
                asistencia.getSesionClase() != null ? asistencia.getSesionClase().getTema() : null,
                asistencia.getMatricula() != null ? asistencia.getMatricula().getId() : null,
                asistencia.getMatricula() != null && asistencia.getMatricula().getEstudiante() != null ? asistencia.getMatricula().getEstudiante().getCodigoEstudiante() : null,
                asistencia.getMatricula() != null && asistencia.getMatricula().getSeccion() != null ? asistencia.getMatricula().getSeccion().getNombre() : null,
                asistencia.getEstado(),
                asistencia.getJustificada()
        );
    }
}