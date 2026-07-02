package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.dto.rol.RolRequestDTO;
import com.rodrigomv.edutrackbackend.dto.rol.RolResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.entity.Rol;
import com.rodrigomv.edutrackbackend.persistence.repository.RolRepository;
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
public class RolService {
    
    private final RolRepository rolRepository;
    
    public List<RolResponseDTO> findAll() {
        return rolRepository.findAll().stream().map(this::toResponse).toList();
    }
    
    public Optional<RolResponseDTO> findById(Long id) {
        return rolRepository.findById(id).map(this::toResponse);
    }
    
    public Optional<RolResponseDTO> findByNombre(String nombre) {
        return rolRepository.findByNombre(nombre).map(this::toResponse);
    }
    
    public RolResponseDTO save(RolRequestDTO request) {
        Rol rol = new Rol();
        rol.setNombre(request.getNombre());
        return toResponse(rolRepository.save(rol));
    }
    
    public RolResponseDTO update(Long id, RolRequestDTO request) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado"));
        rol.setNombre(request.getNombre());
        return toResponse(rolRepository.save(rol));
    }
    
    public void delete(Long id) {
        rolRepository.deleteById(id);
    }
    
    public boolean existsByNombre(String nombre) {
        return rolRepository.existsByNombre(nombre);
    }

    private RolResponseDTO toResponse(Rol rol) {
        return new RolResponseDTO(rol.getId(), rol.getNombre());
    }
}