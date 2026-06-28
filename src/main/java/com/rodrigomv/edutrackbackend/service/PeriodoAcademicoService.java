package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.persistence.entity.PeriodoAcademico;
import com.rodrigomv.edutrackbackend.persistence.enums.PeriodoEstado;
import com.rodrigomv.edutrackbackend.persistence.repository.PeriodoAcademicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PeriodoAcademicoService {
    
    private final PeriodoAcademicoRepository periodoAcademicoRepository;
    
    public List<PeriodoAcademico> findAll() {
        return periodoAcademicoRepository.findAll();
    }
    
    public Optional<PeriodoAcademico> findById(Long id) {
        return periodoAcademicoRepository.findById(id);
    }
    
    public Optional<PeriodoAcademico> findByNombre(String nombre) {
        return periodoAcademicoRepository.findByNombre(nombre);
    }
    
    public List<PeriodoAcademico> findByEstado(PeriodoEstado estado) {
        return periodoAcademicoRepository.findByEstado(estado);
    }
    
    public Optional<PeriodoAcademico> findActivo() {
        return periodoAcademicoRepository.findActivo();
    }
    
    public PeriodoAcademico save(PeriodoAcademico periodo) {
        return periodoAcademicoRepository.save(periodo);
    }
    
    public PeriodoAcademico update(Long id, PeriodoAcademico periodo) {
        periodo.setId(id);
        return periodoAcademicoRepository.save(periodo);
    }
    
    public void delete(Long id) {
        periodoAcademicoRepository.deleteById(id);
    }
}