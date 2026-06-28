package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.persistence.entity.UsuarioRol;
import com.rodrigomv.edutrackbackend.persistence.repository.UsuarioRolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioRolService {
    
    private final UsuarioRolRepository usuarioRolRepository;
    
    public List<UsuarioRol> findAll() {
        return usuarioRolRepository.findAll();
    }
    
    public Optional<UsuarioRol> findById(Long id) {
        return usuarioRolRepository.findById(id);
    }
    
    public List<UsuarioRol> findByUsuario(Long usuarioId) {
        return usuarioRolRepository.findByUsuarioId(usuarioId);
    }
    
    public List<UsuarioRol> findByRol(Long rolId) {
        return usuarioRolRepository.findByRolId(rolId);
    }
    
    public Optional<UsuarioRol> findByUsuarioAndRol(Long usuarioId, Long rolId) {
        return usuarioRolRepository.findByUsuarioIdAndRolId(usuarioId, rolId);
    }
    
    public UsuarioRol save(UsuarioRol usuarioRol) {
        return usuarioRolRepository.save(usuarioRol);
    }
    
    public void delete(Long id) {
        usuarioRolRepository.deleteById(id);
    }
    
    public boolean existsByUsuarioAndRol(Long usuarioId, Long rolId) {
        return usuarioRolRepository.existsByUsuarioIdAndRolId(usuarioId, rolId);
    }
}