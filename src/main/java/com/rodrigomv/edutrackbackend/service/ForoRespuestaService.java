package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.persistence.entity.ForoRespuesta;
import com.rodrigomv.edutrackbackend.persistence.repository.ForoRespuestaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ForoRespuestaService {
    
    private final ForoRespuestaRepository respuestaRepository;
    
    public List<ForoRespuesta> findAll() {
        return respuestaRepository.findAll();
    }
    
    public Optional<ForoRespuesta> findById(Long id) {
        return respuestaRepository.findById(id);
    }
    
    public List<ForoRespuesta> findByForo(Long foroId) {
        return respuestaRepository.findByForoId(foroId);
    }
    
    public List<ForoRespuesta> findByUsuario(Long usuarioId) {
        return respuestaRepository.findByUsuarioId(usuarioId);
    }
    
    public ForoRespuesta save(ForoRespuesta respuesta) {
        if (respuesta.getFechaRespuesta() == null) {
            respuesta.setFechaRespuesta(LocalDateTime.now());
        }
        return respuestaRepository.save(respuesta);
    }
    
    public ForoRespuesta update(Long id, ForoRespuesta respuesta) {
        respuesta.setId(id);
        return respuestaRepository.save(respuesta);
    }
    
    public void delete(Long id) {
        respuestaRepository.deleteById(id);
    }
}