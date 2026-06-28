package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.persistence.entity.Rol;
import com.rodrigomv.edutrackbackend.persistence.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RolService {
    
    private final RolRepository rolRepository;
    
    public List<Rol> findAll() {
        return rolRepository.findAll();
    }
    
    public Optional<Rol> findById(Long id) {
        return rolRepository.findById(id);
    }
    
    public Optional<Rol> findByNombre(String nombre) {
        return rolRepository.findByNombre(nombre);
    }
    
    public Rol save(Rol rol) {
        return rolRepository.save(rol);
    }
    
    public Rol update(Long id, Rol rol) {
        rol.setId(id);
        return rolRepository.save(rol);
    }
    
    public void delete(Long id) {
        rolRepository.deleteById(id);
    }
    
    public boolean existsByNombre(String nombre) {
        return rolRepository.existsByNombre(nombre);
    }
}