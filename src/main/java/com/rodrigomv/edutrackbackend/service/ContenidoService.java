package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.persistence.entity.Contenido;
import com.rodrigomv.edutrackbackend.persistence.enums.ContenidoTipo;
import com.rodrigomv.edutrackbackend.persistence.repository.ContenidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ContenidoService {
    
    private final ContenidoRepository contenidoRepository;
    
    public List<Contenido> findAll() {
        return contenidoRepository.findAll();
    }
    
    public Optional<Contenido> findById(Long id) {
        return contenidoRepository.findById(id);
    }
    
    public List<Contenido> findBySemana(Long semanaId) {
        return contenidoRepository.findBySemanaAcademicaId(semanaId);
    }
    
    public List<Contenido> findByTipo(ContenidoTipo tipo) {
        return contenidoRepository.findByTipo(tipo);
    }
    
    public List<Contenido> findVisibles() {
        return contenidoRepository.findByVisibleTrue();
    }
    
    public Contenido save(Contenido contenido) {
        if (contenido.getFechaPublicacion() == null) {
            contenido.setFechaPublicacion(LocalDateTime.now());
        }
        return contenidoRepository.save(contenido);
    }
    
    public Contenido update(Long id, Contenido contenido) {
        contenido.setId(id);
        return contenidoRepository.save(contenido);
    }
    
    public void delete(Long id) {
        contenidoRepository.deleteById(id);
    }
}