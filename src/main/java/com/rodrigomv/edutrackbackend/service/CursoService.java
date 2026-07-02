package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.dto.curso.CursoRequestDTO;
import com.rodrigomv.edutrackbackend.dto.curso.CursoResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.entity.Curso;
import com.rodrigomv.edutrackbackend.persistence.repository.CursoRepository;
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
public class CursoService {
    
    private final CursoRepository cursoRepository;
    
    public List<CursoResponseDTO> findAll() {
        return cursoRepository.findAll().stream().map(this::toResponse).toList();
    }
    
    public Optional<CursoResponseDTO> findById(Long id) {
        return cursoRepository.findById(id).map(this::toResponse);
    }
    
    public Optional<CursoResponseDTO> findByCodigo(String codigo) {
        return cursoRepository.findByCodigo(codigo).map(this::toResponse);
    }
    
    public CursoResponseDTO save(CursoRequestDTO request) {
        Curso curso = new Curso();
        applyRequest(curso, request);
        return toResponse(cursoRepository.save(curso));
    }
    
    public CursoResponseDTO update(Long id, CursoRequestDTO request) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado"));
        applyRequest(curso, request);
        return toResponse(cursoRepository.save(curso));
    }
    
    public void delete(Long id) {
        cursoRepository.deleteById(id);
    }
    
    public boolean existsByCodigo(String codigo) {
        return cursoRepository.existsByCodigo(codigo);
    }

    private void applyRequest(Curso curso, CursoRequestDTO request) {
        curso.setCodigo(request.codigo());
        curso.setNombre(request.nombre());
        curso.setDescripcion(request.descripcion());
        curso.setCreditos(request.creditos());
    }

    private CursoResponseDTO toResponse(Curso curso) {
        return new CursoResponseDTO(curso.getId(), curso.getCodigo(), curso.getNombre(), curso.getDescripcion(), curso.getCreditos());
    }
}