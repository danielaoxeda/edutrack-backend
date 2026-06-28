package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.persistence.entity.Curso;
import com.rodrigomv.edutrackbackend.persistence.repository.CursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CursoService {
    
    private final CursoRepository cursoRepository;
    
    public List<Curso> findAll() {
        return cursoRepository.findAll();
    }
    
    public Optional<Curso> findById(Long id) {
        return cursoRepository.findById(id);
    }
    
    public Optional<Curso> findByCodigo(String codigo) {
        return cursoRepository.findByCodigo(codigo);
    }
    
    public Curso save(Curso curso) {
        return cursoRepository.save(curso);
    }
    
    public Curso update(Long id, Curso curso) {
        curso.setId(id);
        return cursoRepository.save(curso);
    }
    
    public void delete(Long id) {
        cursoRepository.deleteById(id);
    }
    
    public boolean existsByCodigo(String codigo) {
        return cursoRepository.existsByCodigo(codigo);
    }
}