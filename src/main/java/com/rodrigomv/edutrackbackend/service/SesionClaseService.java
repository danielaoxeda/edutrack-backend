package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.persistence.entity.SesionClase;
import com.rodrigomv.edutrackbackend.persistence.repository.SesionClaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SesionClaseService {
    
    private final SesionClaseRepository sesionClaseRepository;
    
    public List<SesionClase> findAll() {
        return sesionClaseRepository.findAll();
    }
    
    public Optional<SesionClase> findById(Long id) {
        return sesionClaseRepository.findById(id);
    }
    
    public List<SesionClase> findBySemana(Long semanaId) {
        return sesionClaseRepository.findBySemanaAcademicaId(semanaId);
    }
    
    public List<SesionClase> findByFecha(LocalDate fecha) {
        return sesionClaseRepository.findByFecha(fecha);
    }
    
    public List<SesionClase> findByFechaBetween(LocalDate inicio, LocalDate fin) {
        return sesionClaseRepository.findByFechaBetween(inicio, fin);
    }
    
    public SesionClase save(SesionClase sesion) {
        return sesionClaseRepository.save(sesion);
    }
    
    public SesionClase update(Long id, SesionClase sesion) {
        sesion.setId(id);
        return sesionClaseRepository.save(sesion);
    }
    
    public void delete(Long id) {
        sesionClaseRepository.deleteById(id);
    }
}