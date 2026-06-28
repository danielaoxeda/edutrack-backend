package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.persistence.entity.Permiso;
import com.rodrigomv.edutrackbackend.persistence.repository.PermisoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PermisoService {
    
    private final PermisoRepository permisoRepository;
    
    public List<Permiso> findAll() {
        return permisoRepository.findAll();
    }
    
    public Optional<Permiso> findById(Long id) {
        return permisoRepository.findById(id);
    }
    
    public List<Permiso> findByRecurso(String recurso) {
        return permisoRepository.findByRecurso(recurso);
    }
    
    public List<Permiso> findByAccion(String accion) {
        return permisoRepository.findByAccion(accion);
    }
    
    public Permiso save(Permiso permiso) {
        return permisoRepository.save(permiso);
    }
    
    public Permiso update(Long id, Permiso permiso) {
        permiso.setId(id);
        return permisoRepository.save(permiso);
    }
    
    public void delete(Long id) {
        permisoRepository.deleteById(id);
    }
}