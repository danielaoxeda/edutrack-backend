package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.persistence.entity.CriterioEvaluacion;
import com.rodrigomv.edutrackbackend.persistence.repository.CriterioEvaluacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CriterioEvaluacionService {
    
    private final CriterioEvaluacionRepository criterioRepository;
    
    public List<CriterioEvaluacion> findAll() {
        return criterioRepository.findAll();
    }
    
    public Optional<CriterioEvaluacion> findById(Long id) {
        return criterioRepository.findById(id);
    }
    
    public List<CriterioEvaluacion> findBySeccion(Long seccionId) {
        return criterioRepository.findBySeccionId(seccionId);
    }
    
    public CriterioEvaluacion save(CriterioEvaluacion criterio) {
        return criterioRepository.save(criterio);
    }
    
    public CriterioEvaluacion update(Long id, CriterioEvaluacion criterio) {
        criterio.setId(id);
        return criterioRepository.save(criterio);
    }
    
    public void delete(Long id) {
        criterioRepository.deleteById(id);
    }
}