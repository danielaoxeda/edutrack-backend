package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.persistence.entity.Entrega;
import com.rodrigomv.edutrackbackend.persistence.enums.EntregaEstado;
import com.rodrigomv.edutrackbackend.persistence.repository.EntregaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EntregaService {
    
    private final EntregaRepository entregaRepository;
    
    public List<Entrega> findAll() {
        return entregaRepository.findAll();
    }
    
    public Optional<Entrega> findById(Long id) {
        return entregaRepository.findById(id);
    }
    
    public List<Entrega> findByActividad(Long actividadId) {
        return entregaRepository.findByActividadId(actividadId);
    }
    
    public List<Entrega> findByMatricula(Long matriculaId) {
        return entregaRepository.findByMatriculaId(matriculaId);
    }
    
    public List<Entrega> findByEstado(EntregaEstado estado) {
        return entregaRepository.findByEstado(estado);
    }
    
    public Optional<Entrega> findByActividadAndMatricula(Long actividadId, Long matriculaId) {
        return entregaRepository.findByActividadIdAndMatriculaId(actividadId, matriculaId);
    }
    
    public Entrega save(Entrega entrega) {
        if (entrega.getFechaEntrega() == null) {
            entrega.setFechaEntrega(LocalDateTime.now());
        }
        return entregaRepository.save(entrega);
    }
    
    public Entrega update(Long id, Entrega entrega) {
        entrega.setId(id);
        return entregaRepository.save(entrega);
    }
    
    public void delete(Long id) {
        entregaRepository.deleteById(id);
    }
    
    public boolean existsByActividadAndMatricula(Long actividadId, Long matriculaId) {
        return entregaRepository.existsByActividadIdAndMatriculaId(actividadId, matriculaId);
    }
}