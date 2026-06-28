package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.persistence.entity.DocenteSeccion;
import com.rodrigomv.edutrackbackend.persistence.repository.DocenteSeccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DocenteSeccionService {
    
    private final DocenteSeccionRepository docenteSeccionRepository;
    
    public List<DocenteSeccion> findAll() {
        return docenteSeccionRepository.findAll();
    }
    
    public Optional<DocenteSeccion> findById(Long id) {
        return docenteSeccionRepository.findById(id);
    }
    
    public List<DocenteSeccion> findByDocente(Long docenteId) {
        return docenteSeccionRepository.findByDocenteId(docenteId);
    }
    
    public List<DocenteSeccion> findBySeccion(Long seccionId) {
        return docenteSeccionRepository.findBySeccionId(seccionId);
    }
    
    public Optional<DocenteSeccion> findByDocenteAndSeccion(Long docenteId, Long seccionId) {
        return docenteSeccionRepository.findByDocenteIdAndSeccionId(docenteId, seccionId);
    }
    
    public DocenteSeccion save(DocenteSeccion docenteSeccion) {
        return docenteSeccionRepository.save(docenteSeccion);
    }
    
    public void delete(Long id) {
        docenteSeccionRepository.deleteById(id);
    }
    
    public boolean existsByDocenteAndSeccion(Long docenteId, Long seccionId) {
        return docenteSeccionRepository.existsByDocenteIdAndSeccionId(docenteId, seccionId);
    }
}