package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.persistence.entity.Estudiante;
import com.rodrigomv.edutrackbackend.persistence.enums.EstadoAcademico;
import com.rodrigomv.edutrackbackend.persistence.repository.EstudianteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EstudianteService {
    
    private final EstudianteRepository estudianteRepository;
    
    public List<Estudiante> findAll() {
        return estudianteRepository.findAll();
    }
    
    public Optional<Estudiante> findById(Long id) {
        return estudianteRepository.findById(id);
    }
    
    public Optional<Estudiante> findByCodigo(String codigo) {
        return estudianteRepository.findByCodigoEstudiante(codigo);
    }
    
    public List<Estudiante> findByEstadoAcademico(EstadoAcademico estado) {
        return estudianteRepository.findByEstadoAcademico(estado);
    }
    
    public Estudiante save(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }
    
    public Estudiante update(Long id, Estudiante estudiante) {
        estudiante.setId(id);
        return estudianteRepository.save(estudiante);
    }
    
    public void delete(Long id) {
        estudianteRepository.deleteById(id);
    }
    
    public boolean existsByCodigo(String codigo) {
        return estudianteRepository.existsByCodigoEstudiante(codigo);
    }
}