package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.dto.permiso.PermisoRequestDTO;
import com.rodrigomv.edutrackbackend.dto.permiso.PermisoResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.entity.Permiso;
import com.rodrigomv.edutrackbackend.persistence.repository.PermisoRepository;
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
public class PermisoService {
    
    private final PermisoRepository permisoRepository;
    
    public List<PermisoResponseDTO> findAll() {
        return permisoRepository.findAll().stream().map(this::toResponse).toList();
    }
    
    public Optional<PermisoResponseDTO> findById(Long id) {
        return permisoRepository.findById(id).map(this::toResponse);
    }
    
    public List<PermisoResponseDTO> findByRecurso(String recurso) {
        return permisoRepository.findByRecurso(recurso).stream().map(this::toResponse).toList();
    }
    
    public List<PermisoResponseDTO> findByAccion(String accion) {
        return permisoRepository.findByAccion(accion).stream().map(this::toResponse).toList();
    }
    
    public PermisoResponseDTO save(PermisoRequestDTO request) {
        Permiso permiso = new Permiso();
        permiso.setNombre(request.getNombre());
        permiso.setRecurso(request.getRecurso());
        permiso.setAccion(request.getAccion());
        return toResponse(permisoRepository.save(permiso));
    }
    
    public PermisoResponseDTO update(Long id, PermisoRequestDTO request) {
        Permiso permiso = permisoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Permiso no encontrado"));
        permiso.setNombre(request.getNombre());
        permiso.setRecurso(request.getRecurso());
        permiso.setAccion(request.getAccion());
        return toResponse(permisoRepository.save(permiso));
    }
    
    public void delete(Long id) {
        permisoRepository.deleteById(id);
    }

    private PermisoResponseDTO toResponse(Permiso permiso) {
        return new PermisoResponseDTO(permiso.getId(), permiso.getNombre(), permiso.getRecurso(), permiso.getAccion());
    }
}