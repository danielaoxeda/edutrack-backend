package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.persistence.entity.Foro;
import com.rodrigomv.edutrackbackend.persistence.repository.ForoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ForoService {
    
    private final ForoRepository foroRepository;
    
    public List<Foro> findAll() {
        return foroRepository.findAll();
    }
    
    public Optional<Foro> findById(Long id) {
        return foroRepository.findById(id);
    }
    
    public List<Foro> findBySemana(Long semanaId) {
        return foroRepository.findBySemanaAcademicaId(semanaId);
    }
    
    public List<Foro> findByUsuario(Long usuarioId) {
        return foroRepository.findByCreadoPorId(usuarioId);
    }
    
    public Foro save(Foro foro) {
        if (foro.getFechaCreacion() == null) {
            foro.setFechaCreacion(LocalDateTime.now());
        }
        return foroRepository.save(foro);
    }
    
    public Foro update(Long id, Foro foro) {
        foro.setId(id);
        return foroRepository.save(foro);
    }
    
    public void delete(Long id) {
        foroRepository.deleteById(id);
    }
}