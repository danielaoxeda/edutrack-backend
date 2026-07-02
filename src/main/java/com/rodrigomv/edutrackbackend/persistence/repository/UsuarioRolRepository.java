package com.rodrigomv.edutrackbackend.persistence.repository;

import com.rodrigomv.edutrackbackend.persistence.entity.UsuarioRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, Long> {
    
    List<UsuarioRol> findByUsuarioId(Long usuarioId);
    
    List<UsuarioRol> findByRolId(Long rolId);
    
    Optional<UsuarioRol> findByUsuarioIdAndRolId(Long usuarioId, Long rolId);
    
    boolean existsByUsuarioIdAndRolId(Long usuarioId, Long rolId);
}