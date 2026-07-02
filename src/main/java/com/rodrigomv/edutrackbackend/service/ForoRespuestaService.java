package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.dto.foroRespuesta.ForoRespuestaRequestDTO;
import com.rodrigomv.edutrackbackend.dto.foroRespuesta.ForoRespuestaResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.entity.Foro;
import com.rodrigomv.edutrackbackend.persistence.entity.ForoRespuesta;
import com.rodrigomv.edutrackbackend.persistence.entity.Usuario;
import com.rodrigomv.edutrackbackend.persistence.repository.ForoRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.ForoRespuestaRepository;
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
public class ForoRespuestaService {
    
    private final ForoRespuestaRepository respuestaRepository;
    private final ForoRepository foroRepository;
    private final UsuarioRepository usuarioRepository;
    
    public List<ForoRespuestaResponseDTO> findAll() {
        return respuestaRepository.findAll().stream().map(this::toResponse).toList();
    }
    
    public Optional<ForoRespuestaResponseDTO> findById(Long id) {
        return respuestaRepository.findById(id).map(this::toResponse);
    }
    
    public List<ForoRespuestaResponseDTO> findByForo(Long foroId) {
        return respuestaRepository.findByForoId(foroId).stream().map(this::toResponse).toList();
    }
    
    public List<ForoRespuestaResponseDTO> findByUsuario(Long usuarioId) {
        return respuestaRepository.findByUsuarioId(usuarioId).stream().map(this::toResponse).toList();
    }
    
    public ForoRespuestaResponseDTO save(ForoRespuestaRequestDTO request) {
        ForoRespuesta respuesta = new ForoRespuesta();
        applyRequest(respuesta, request);
        return toResponse(respuestaRepository.save(respuesta));
    }
    
    public ForoRespuestaResponseDTO update(Long id, ForoRespuestaRequestDTO request) {
        ForoRespuesta respuesta = respuestaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Respuesta no encontrada"));
        applyRequest(respuesta, request);
        return toResponse(respuestaRepository.save(respuesta));
    }
    
    public void delete(Long id) {
        respuestaRepository.deleteById(id);
    }

    private void applyRequest(ForoRespuesta respuesta, ForoRespuestaRequestDTO request) {
        Foro foro = foroRepository.findById(request.foroId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Foro no encontrado"));
        Usuario usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        respuesta.setForo(foro);
        respuesta.setUsuario(usuario);
        respuesta.setMensaje(request.mensaje());
        if (respuesta.getFechaRespuesta() == null) {
            respuesta.setFechaRespuesta(LocalDateTime.now());
        }
    }

    private ForoRespuestaResponseDTO toResponse(ForoRespuesta respuesta) {
        return new ForoRespuestaResponseDTO(
                respuesta.getId(),
                respuesta.getForo() != null ? respuesta.getForo().getId() : null,
                respuesta.getForo() != null ? respuesta.getForo().getTitulo() : null,
                respuesta.getUsuario() != null ? respuesta.getUsuario().getId() : null,
                respuesta.getUsuario() != null ? respuesta.getUsuario().getEmail() : null,
                respuesta.getMensaje(),
                respuesta.getFechaRespuesta()
        );
    }
}