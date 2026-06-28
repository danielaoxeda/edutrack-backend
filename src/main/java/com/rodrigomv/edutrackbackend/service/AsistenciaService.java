package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.persistence.entity.Asistencia;
import com.rodrigomv.edutrackbackend.persistence.enums.AsistenciaEstado;
import com.rodrigomv.edutrackbackend.persistence.repository.AsistenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AsistenciaService {
    
    private final AsistenciaRepository asistenciaRepository;
    
    public List<Asistencia> findAll() {
        return asistenciaRepository.findAll();
    }
    
    public Optional<Asistencia> findById(Long id) {
        return asistenciaRepository.findById(id);
    }
    
    public List<Asistencia> findBySesion(Long sesionId) {
        return asistenciaRepository.findBySesionClaseId(sesionId);
    }
    
    public List<Asistencia> findByMatricula(Long matriculaId) {
        return asistenciaRepository.findByMatriculaId(matriculaId);
    }
    
    public List<Asistencia> findByEstado(AsistenciaEstado estado) {
        return asistenciaRepository.findByEstado(estado);
    }
    
    public Optional<Asistencia> findBySesionAndMatricula(Long sesionId, Long matriculaId) {
        return asistenciaRepository.findBySesionClaseIdAndMatriculaId(sesionId, matriculaId);
    }
    
    public Asistencia save(Asistencia asistencia) {
        return asistenciaRepository.save(asistencia);
    }
    
    public Asistencia update(Long id, Asistencia asistencia) {
        asistencia.setId(id);
        return asistenciaRepository.save(asistencia);
    }
    
    public void delete(Long id) {
        asistenciaRepository.deleteById(id);
    }
}