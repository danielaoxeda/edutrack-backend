package com.rodrigomv.edutrackbackend.persistence.repository;

import com.rodrigomv.edutrackbackend.persistence.entity.RolPermiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolPermisoRepository extends JpaRepository<RolPermiso, Long> {
    
    List<RolPermiso> findByRolId(Long rolId);
    
    List<RolPermiso> findByPermisoId(Long permisoId);
    
    Optional<RolPermiso> findByRolIdAndPermisoId(Long rolId, Long permisoId);
    
    boolean existsByRolIdAndPermisoId(Long rolId, Long permisoId);
}