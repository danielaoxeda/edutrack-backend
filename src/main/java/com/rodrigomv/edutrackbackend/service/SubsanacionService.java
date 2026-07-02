package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.dto.subsanacion.SubsanacionRequestDTO;
import com.rodrigomv.edutrackbackend.dto.subsanacion.SubsanacionResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.entity.Entrega;
import com.rodrigomv.edutrackbackend.persistence.entity.Subsanacion;
import com.rodrigomv.edutrackbackend.persistence.repository.EntregaRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.SubsanacionRepository;
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
public class SubsanacionService {
    
    private final SubsanacionRepository subsanacionRepository;
    private final EntregaRepository entregaRepository;
    
    public List<SubsanacionResponseDTO> findAll() {
        return subsanacionRepository.findAll().stream().map(this::toResponse).toList();
    }
    
    public Optional<SubsanacionResponseDTO> findById(Long id) {
        return subsanacionRepository.findById(id).map(this::toResponse);
    }
    
    public List<SubsanacionResponseDTO> findByEntrega(Long entregaId) {
        return subsanacionRepository.findByEntregaId(entregaId).stream().map(this::toResponse).toList();
    }
    
    public SubsanacionResponseDTO save(SubsanacionRequestDTO request) {
        Subsanacion subsanacion = new Subsanacion();
        applyRequest(subsanacion, request);
        return toResponse(subsanacionRepository.save(subsanacion));
    }
    
    public SubsanacionResponseDTO update(Long id, SubsanacionRequestDTO request) {
        Subsanacion subsanacion = subsanacionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subsanación no encontrada"));
        applyRequest(subsanacion, request);
        return toResponse(subsanacionRepository.save(subsanacion));
    }
    
    public void delete(Long id) {
        subsanacionRepository.deleteById(id);
    }

    private void applyRequest(Subsanacion subsanacion, SubsanacionRequestDTO request) {
        Entrega entrega = entregaRepository.findById(request.entregaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entrega no encontrada"));
        subsanacion.setEntrega(entrega);
        subsanacion.setNotaAnterior(request.notaAnterior());
        subsanacion.setNuevaNota(request.nuevaNota());
        subsanacion.setMotivo(request.motivo());
        if (subsanacion.getFechaSubsanacion() == null) {
            subsanacion.setFechaSubsanacion(LocalDateTime.now());
        }
    }

    private SubsanacionResponseDTO toResponse(Subsanacion subsanacion) {
        return new SubsanacionResponseDTO(
                subsanacion.getId(),
                subsanacion.getEntrega() != null ? subsanacion.getEntrega().getId() : null,
                subsanacion.getEntrega() != null && subsanacion.getEntrega().getActividad() != null ? subsanacion.getEntrega().getActividad().getId() : null,
                subsanacion.getEntrega() != null && subsanacion.getEntrega().getActividad() != null ? subsanacion.getEntrega().getActividad().getTitulo() : null,
                subsanacion.getEntrega() != null && subsanacion.getEntrega().getMatricula() != null ? subsanacion.getEntrega().getMatricula().getId() : null,
                subsanacion.getEntrega() != null && subsanacion.getEntrega().getMatricula() != null && subsanacion.getEntrega().getMatricula().getEstudiante() != null ? subsanacion.getEntrega().getMatricula().getEstudiante().getCodigoEstudiante() : null,
                subsanacion.getNotaAnterior(),
                subsanacion.getNuevaNota(),
                subsanacion.getMotivo(),
                subsanacion.getFechaSubsanacion()
        );
    }
}