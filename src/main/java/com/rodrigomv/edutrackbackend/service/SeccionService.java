package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.persistence.entity.Seccion;
import com.rodrigomv.edutrackbackend.persistence.repository.SeccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SeccionService {
    
    private final SeccionRepository seccionRepository;
    
    public List<Seccion> findAll() {
        return seccionRepository.findAll();
    }
    
    public Optional<Seccion> findById(Long id) {
        return seccionRepository.findById(id);
    }
    
    public List<Seccion> findByPeriodo(Long periodoId) {
        return seccionRepository.findByPeriodoAcademicoId(periodoId);
    }
    
    public List<Seccion> findByCurso(Long cursoId) {
        return seccionRepository.findByCursoId(cursoId);
    }
    
    public Seccion save(Seccion seccion) {
        return seccionRepository.save(seccion);
    }
    
    public Seccion update(Long id, Seccion seccion) {
        seccion.setId(id);
        return seccionRepository.save(seccion);
    }
    
    public void delete(Long id) {
        seccionRepository.deleteById(id);
    }
}