package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.persistence.entity.RolPermiso;
import com.rodrigomv.edutrackbackend.persistence.repository.RolPermisoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RolPermisoService {
    
    private final RolPermisoRepository rolPermisoRepository;
    
    public List<RolPermiso> findAll() {
        return rolPermisoRepository.findAll();
    }
    
    public Optional<RolPermiso> findById(Long id) {
        return rolPermisoRepository.findById(id);
    }
    
    public List<RolPermiso> findByRol(Long rolId) {
        return rolPermisoRepository.findByRolId(rolId);
    }
    
    public List<RolPermiso> findByPermiso(Long permisoId) {
        return rolPermisoRepository.findByPermisoId(permisoId);
    }
    
    public Optional<RolPermiso> findByRolAndPermiso(Long rolId, Long permisoId) {
        return rolPermisoRepository.findByRolIdAndPermisoId(rolId, permisoId);
    }
    
    public RolPermiso save(RolPermiso rolPermiso) {
        return rolPermisoRepository.save(rolPermiso);
    }
    
    public void delete(Long id) {
        rolPermisoRepository.deleteById(id);
    }
    
    public boolean existsByRolAndPermiso(Long rolId, Long permisoId) {
        return rolPermisoRepository.existsByRolIdAndPermisoId(rolId, permisoId);
    }
}