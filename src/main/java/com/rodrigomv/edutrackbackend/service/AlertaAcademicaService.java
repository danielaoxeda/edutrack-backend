package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.persistence.entity.AlertaAcademica;
import com.rodrigomv.edutrackbackend.persistence.enums.AlertaTipo;
import com.rodrigomv.edutrackbackend.persistence.repository.AlertaAcademicaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AlertaAcademicaService {
    
    private final AlertaAcademicaRepository alertaRepository;
    
    public List<AlertaAcademica> findAll() {
        return alertaRepository.findAll();
    }
    
    public Optional<AlertaAcademica> findById(Long id) {
        return alertaRepository.findById(id);
    }
    
    public List<AlertaAcademica> findByMatricula(Long matriculaId) {
        return alertaRepository.findByMatriculaId(matriculaId);
    }
    
    public List<AlertaAcademica> findByTipo(AlertaTipo tipo) {
        return alertaRepository.findByTipo(tipo);
    }
    
    public AlertaAcademica save(AlertaAcademica alerta) {
        if (alerta.getFechaAlerta() == null) {
            alerta.setFechaAlerta(LocalDateTime.now());
        }
        return alertaRepository.save(alerta);
    }
    
    public AlertaAcademica update(Long id, AlertaAcademica alerta) {
        alerta.setId(id);
        return alertaRepository.save(alerta);
    }
    
    public void delete(Long id) {
        alertaRepository.deleteById(id);
    }
}