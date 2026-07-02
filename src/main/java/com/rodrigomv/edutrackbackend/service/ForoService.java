package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.dto.foro.ForoRequestDTO;
import com.rodrigomv.edutrackbackend.dto.foro.ForoResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.entity.Foro;
import com.rodrigomv.edutrackbackend.persistence.entity.SemanaAcademica;
import com.rodrigomv.edutrackbackend.persistence.entity.Usuario;
import com.rodrigomv.edutrackbackend.persistence.repository.ForoRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.SemanaAcademicaRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.UsuarioRepository;
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
public class ForoService {
    
    private final ForoRepository foroRepository;
    private final SemanaAcademicaRepository semanaAcademicaRepository;
    private final UsuarioRepository usuarioRepository;
    
    public List<ForoResponseDTO> findAll() {
        return foroRepository.findAll().stream().map(this::toResponse).toList();
    }
    
    public Optional<ForoResponseDTO> findById(Long id) {
        return foroRepository.findById(id).map(this::toResponse);
    }
    
    public List<ForoResponseDTO> findBySemana(Long semanaId) {
        return foroRepository.findBySemanaAcademicaId(semanaId).stream().map(this::toResponse).toList();
    }
    
    public List<ForoResponseDTO> findByUsuario(Long usuarioId) {
        return foroRepository.findByCreadoPorId(usuarioId).stream().map(this::toResponse).toList();
    }
    
    public ForoResponseDTO save(ForoRequestDTO request) {
        Foro foro = new Foro();
        applyRequest(foro, request);
        return toResponse(foroRepository.save(foro));
    }
    
    public ForoResponseDTO update(Long id, ForoRequestDTO request) {
        Foro foro = foroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Foro no encontrado"));
        applyRequest(foro, request);
        return toResponse(foroRepository.save(foro));
    }
    
    public void delete(Long id) {
        foroRepository.deleteById(id);
    }

    private void applyRequest(Foro foro, ForoRequestDTO request) {
        SemanaAcademica semana = semanaAcademicaRepository.findById(request.semanaAcademicaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Semana académica no encontrada"));
        Usuario creadoPor = usuarioRepository.findById(request.creadoPorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        foro.setSemanaAcademica(semana);
        foro.setCreadoPor(creadoPor);
        foro.setTitulo(request.titulo());
        foro.setMensajePrincipal(request.mensajePrincipal());
        if (foro.getFechaCreacion() == null) {
            foro.setFechaCreacion(LocalDateTime.now());
        }
    }

    private ForoResponseDTO toResponse(Foro foro) {
        return new ForoResponseDTO(
                foro.getId(),
                foro.getSemanaAcademica() != null ? foro.getSemanaAcademica().getId() : null,
                foro.getSemanaAcademica() != null ? foro.getSemanaAcademica().getTitulo() : null,
                foro.getCreadoPor() != null ? foro.getCreadoPor().getId() : null,
                foro.getCreadoPor() != null ? foro.getCreadoPor().getEmail() : null,
                foro.getTitulo(),
                foro.getMensajePrincipal(),
                foro.getFechaCreacion()
        );
    }
}