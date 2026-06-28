package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.persistence.entity.Subsanacion;
import com.rodrigomv.edutrackbackend.persistence.repository.SubsanacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SubsanacionService {
    
    private final SubsanacionRepository subsanacionRepository;
    
    public List<Subsanacion> findAll() {
        return subsanacionRepository.findAll();
    }
    
    public Optional<Subsanacion> findById(Long id) {
        return subsanacionRepository.findById(id);
    }
    
    public List<Subsanacion> findByEntrega(Long entregaId) {
        return subsanacionRepository.findByEntregaId(entregaId);
    }
    
    public Subsanacion save(Subsanacion subsanacion) {
        if (subsanacion.getFechaSubsanacion() == null) {
            subsanacion.setFechaSubsanacion(LocalDateTime.now());
        }
        return subsanacionRepository.save(subsanacion);
    }
    
    public Subsanacion update(Long id, Subsanacion subsanacion) {
        subsanacion.setId(id);
        return subsanacionRepository.save(subsanacion);
    }
    
    public void delete(Long id) {
        subsanacionRepository.deleteById(id);
    }
}