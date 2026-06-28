package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.persistence.entity.Matricula;
import com.rodrigomv.edutrackbackend.persistence.enums.MatriculaEstado;
import com.rodrigomv.edutrackbackend.persistence.repository.MatriculaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MatriculaService {
    
    private final MatriculaRepository matriculaRepository;
    
    public List<Matricula> findAll() {
        return matriculaRepository.findAll();
    }
    
    public Optional<Matricula> findById(Long id) {
        return matriculaRepository.findById(id);
    }
    
    public List<Matricula> findByEstudiante(Long estudianteId) {
        return matriculaRepository.findByEstudianteId(estudianteId);
    }
    
    public List<Matricula> findBySeccion(Long seccionId) {
        return matriculaRepository.findBySeccionId(seccionId);
    }
    
    public List<Matricula> findByEstado(MatriculaEstado estado) {
        return matriculaRepository.findByEstado(estado);
    }
    
    public Matricula save(Matricula matricula) {
        if (matricula.getFechaMatricula() == null) {
            matricula.setFechaMatricula(LocalDateTime.now());
        }
        return matriculaRepository.save(matricula);
    }
    
    public Matricula update(Long id, Matricula matricula) {
        matricula.setId(id);
        return matriculaRepository.save(matricula);
    }
    
    public void delete(Long id) {
        matriculaRepository.deleteById(id);
    }
    
    public boolean existsByEstudianteAndSeccion(Long estudianteId, Long seccionId) {
        return matriculaRepository.existsByEstudianteIdAndSeccionId(estudianteId, seccionId);
    }
}