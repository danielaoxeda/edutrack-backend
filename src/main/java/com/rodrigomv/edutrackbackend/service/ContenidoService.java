package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.dto.contenido.ContenidoRequestDTO;
import com.rodrigomv.edutrackbackend.dto.contenido.ContenidoResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.entity.Contenido;
import com.rodrigomv.edutrackbackend.persistence.entity.SemanaAcademica;
import com.rodrigomv.edutrackbackend.persistence.enums.ContenidoTipo;
import com.rodrigomv.edutrackbackend.persistence.repository.ContenidoRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.SemanaAcademicaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ContenidoService {
    
    private final ContenidoRepository contenidoRepository;
    private final SemanaAcademicaRepository semanaAcademicaRepository;
    
    public List<ContenidoResponseDTO> findAll() {
        return contenidoRepository.findAll().stream().map(this::toResponse).toList();
    }
    
    public Optional<ContenidoResponseDTO> findById(Long id) {
        return contenidoRepository.findById(id).map(this::toResponse);
    }
    
    public List<ContenidoResponseDTO> findBySemana(Long semanaId) {
        return contenidoRepository.findBySemanaAcademicaId(semanaId).stream().map(this::toResponse).toList();
    }
    
    public List<ContenidoResponseDTO> findByTipo(ContenidoTipo tipo) {
        return contenidoRepository.findByTipo(tipo).stream().map(this::toResponse).toList();
    }
    
    public List<ContenidoResponseDTO> findVisibles() {
        return contenidoRepository.findByVisibleTrue().stream().map(this::toResponse).toList();
    }
    
    public ContenidoResponseDTO save(ContenidoRequestDTO request) {
        Contenido contenido = new Contenido();
        applyRequest(contenido, request);
        return toResponse(contenidoRepository.save(contenido));
    }
    
    public ContenidoResponseDTO update(Long id, ContenidoRequestDTO request) {
        Contenido contenido = contenidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contenido no encontrado"));
        applyRequest(contenido, request);
        return toResponse(contenidoRepository.save(contenido));
    }
    
    public void delete(Long id) {
        contenidoRepository.deleteById(id);
    }

    private void applyRequest(Contenido contenido, ContenidoRequestDTO request) {
        SemanaAcademica semanaAcademica = semanaAcademicaRepository.findById(request.semanaAcademicaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Semana académica no encontrada"));
        contenido.setSemanaAcademica(semanaAcademica);
        contenido.setTitulo(request.titulo());
        contenido.setDescripcion(request.descripcion());
        contenido.setTipo(request.tipo());
        contenido.setUrlRecurso(request.urlRecurso());
        contenido.setVisible(request.visible() != null ? request.visible() : (contenido.getVisible() != null ? contenido.getVisible() : true));
        if (contenido.getFechaPublicacion() == null) {
            contenido.setFechaPublicacion(LocalDateTime.now());
        }
    }

    private ContenidoResponseDTO toResponse(Contenido contenido) {
        return new ContenidoResponseDTO(
                contenido.getId(),
                contenido.getSemanaAcademica() != null ? contenido.getSemanaAcademica().getId() : null,
                contenido.getSemanaAcademica() != null ? contenido.getSemanaAcademica().getTitulo() : null,
                contenido.getTitulo(),
                contenido.getDescripcion(),
                contenido.getTipo(),
                contenido.getUrlRecurso(),
                contenido.getVisible(),
                contenido.getFechaPublicacion()
        );
    }
}