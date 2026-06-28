package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.persistence.entity.SemanaAcademica;
import com.rodrigomv.edutrackbackend.persistence.repository.SemanaAcademicaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SemanaAcademicaService {
    
    private final SemanaAcademicaRepository semanaRepository;
    
    public List<SemanaAcademica> findAll() {
        return semanaRepository.findAll();
    }
    
    public Optional<SemanaAcademica> findById(Long id) {
        return semanaRepository.findById(id);
    }
    
    public List<SemanaAcademica> findBySeccion(Long seccionId) {
        return semanaRepository.findBySeccionIdOrderByNumeroSemanaAsc(seccionId);
    }
    
    public SemanaAcademica save(SemanaAcademica semana) {
        return semanaRepository.save(semana);
    }
    
    public SemanaAcademica update(Long id, SemanaAcademica semana) {
        semana.setId(id);
        return semanaRepository.save(semana);
    }
    
    public void delete(Long id) {
        semanaRepository.deleteById(id);
    }
}