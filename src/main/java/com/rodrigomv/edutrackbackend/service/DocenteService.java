package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.persistence.entity.Docente;
import com.rodrigomv.edutrackbackend.persistence.repository.DocenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DocenteService {
    
    private final DocenteRepository docenteRepository;
    
    public List<Docente> findAll() {
        return docenteRepository.findAll();
    }
    
    public Optional<Docente> findById(Long id) {
        return docenteRepository.findById(id);
    }
    
    public Optional<Docente> findByCodigo(String codigo) {
        return docenteRepository.findByCodigoDocente(codigo);
    }
    
    public Docente save(Docente docente) {
        return docenteRepository.save(docente);
    }
    
    public Docente update(Long id, Docente docente) {
        docente.setId(id);
        return docenteRepository.save(docente);
    }
    
    public void delete(Long id) {
        docenteRepository.deleteById(id);
    }
    
    public boolean existsByCodigo(String codigo) {
        return docenteRepository.existsByCodigoDocente(codigo);
    }
}