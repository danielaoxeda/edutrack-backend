package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.persistence.entity.Actividad;
import com.rodrigomv.edutrackbackend.persistence.enums.ActividadTipo;
import com.rodrigomv.edutrackbackend.persistence.repository.ActividadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ActividadService {
    
    private final ActividadRepository actividadRepository;
    
    public List<Actividad> findAll() {
        return actividadRepository.findAll();
    }
    
    public Optional<Actividad> findById(Long id) {
        return actividadRepository.findById(id);
    }
    
    public List<Actividad> findBySemana(Long semanaId) {
        return actividadRepository.findBySemanaAcademicaId(semanaId);
    }
    
    public List<Actividad> findByTipo(ActividadTipo tipo) {
        return actividadRepository.findByTipo(tipo);
    }
    
    public List<Actividad> findVisibles() {
        return actividadRepository.findByVisibleTrue();
    }
    
    public List<Actividad> findByFechaLimiteBetween(LocalDateTime inicio, LocalDateTime fin) {
        return actividadRepository.findByFechaLimiteBetween(inicio, fin);
    }
    
    public Actividad save(Actividad actividad) {
        return actividadRepository.save(actividad);
    }
    
    public Actividad update(Long id, Actividad actividad) {
        actividad.setId(id);
        return actividadRepository.save(actividad);
    }
    
    public void delete(Long id) {
        actividadRepository.deleteById(id);
    }
}