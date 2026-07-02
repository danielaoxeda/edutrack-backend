package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.dto.rolPermiso.RolPermisoRequestDTO;
import com.rodrigomv.edutrackbackend.dto.rolPermiso.RolPermisoResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.entity.Permiso;
import com.rodrigomv.edutrackbackend.persistence.entity.Rol;
import com.rodrigomv.edutrackbackend.persistence.entity.RolPermiso;
import com.rodrigomv.edutrackbackend.persistence.repository.PermisoRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.RolRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.RolPermisoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RolPermisoService {
    
    private final RolPermisoRepository rolPermisoRepository;
    private final RolRepository rolRepository;
    private final PermisoRepository permisoRepository;
    
    public List<RolPermisoResponseDTO> findAll() {
        return rolPermisoRepository.findAll().stream().map(this::toResponse).toList();
    }
    
    public Optional<RolPermisoResponseDTO> findById(Long id) {
        return rolPermisoRepository.findById(id).map(this::toResponse);
    }
    
    public List<RolPermisoResponseDTO> findByRol(Long rolId) {
        return rolPermisoRepository.findByRolId(rolId).stream().map(this::toResponse).toList();
    }
    
    public List<RolPermisoResponseDTO> findByPermiso(Long permisoId) {
        return rolPermisoRepository.findByPermisoId(permisoId).stream().map(this::toResponse).toList();
    }
    
    public Optional<RolPermisoResponseDTO> findByRolAndPermiso(Long rolId, Long permisoId) {
        return rolPermisoRepository.findByRolIdAndPermisoId(rolId, permisoId).map(this::toResponse);
    }
    
    public RolPermisoResponseDTO save(RolPermisoRequestDTO request) {
        Rol rol = rolRepository.findById(request.rolId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado"));
        Permiso permiso = permisoRepository.findById(request.permisoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Permiso no encontrado"));

        if (rolPermisoRepository.existsByRolIdAndPermisoId(rol.getId(), permiso.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La relación rol-permiso ya existe");
        }

        RolPermiso rolPermiso = new RolPermiso();
        rolPermiso.setRol(rol);
        rolPermiso.setPermiso(permiso);
        return toResponse(rolPermisoRepository.save(rolPermiso));
    }
    
    public void delete(Long id) {
        rolPermisoRepository.deleteById(id);
    }
    
    public boolean existsByRolAndPermiso(Long rolId, Long permisoId) {
        return rolPermisoRepository.existsByRolIdAndPermisoId(rolId, permisoId);
    }

    private RolPermisoResponseDTO toResponse(RolPermiso rolPermiso) {
        return new RolPermisoResponseDTO(
                rolPermiso.getId(),
                rolPermiso.getRol() != null ? rolPermiso.getRol().getId() : null,
                rolPermiso.getRol() != null ? rolPermiso.getRol().getNombre() : null,
                rolPermiso.getPermiso() != null ? rolPermiso.getPermiso().getId() : null,
                rolPermiso.getPermiso() != null ? rolPermiso.getPermiso().getNombre() : null
        );
    }
}