package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.dto.estudiante.EstudianteRequestDTO;
import com.rodrigomv.edutrackbackend.dto.estudiante.EstudianteResponseDTO;
import com.rodrigomv.edutrackbackend.dto.usuario.UsuarioRequestDTO;
import com.rodrigomv.edutrackbackend.dto.usuario.UsuarioResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.entity.Estudiante;
import com.rodrigomv.edutrackbackend.persistence.entity.Usuario;
import com.rodrigomv.edutrackbackend.persistence.enums.EstadoAcademico;
import com.rodrigomv.edutrackbackend.persistence.repository.EstudianteRepository;
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
public class EstudianteService {
    
    private final EstudianteRepository estudianteRepository;
    private final UsuarioService usuarioService;
    
    public List<EstudianteResponseDTO> findAll() {
        return estudianteRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }
    
    public Optional<EstudianteResponseDTO> findById(Long id) {
        return estudianteRepository.findById(id).map(this::toResponse);
    }
    
    public Optional<EstudianteResponseDTO> findByCodigo(String codigo) {
        return estudianteRepository.findByCodigoEstudiante(codigo).map(this::toResponse);
    }
    
    public List<EstudianteResponseDTO> findByEstadoAcademico(EstadoAcademico estado) {
        return estudianteRepository.findByEstadoAcademico(estado).stream()
                .map(this::toResponse)
                .toList();
    }
    
    public EstudianteResponseDTO save(EstudianteRequestDTO request) {
        Usuario usuario = buildUsuario(request.usuario(), true, null);
        usuario = usuarioService.saveEntity(usuario);

        Estudiante estudiante = new Estudiante();
        estudiante.setUsuario(usuario);
        estudiante.setCodigoEstudiante(request.codigoEstudiante());
        estudiante.setEstadoAcademico(request.estadoAcademico() != null ? request.estadoAcademico() : EstadoAcademico.REGULAR);
        usuario.setEstudiante(estudiante);

        return toResponse(estudianteRepository.save(estudiante));
    }
    
    public EstudianteResponseDTO update(Long id, EstudianteRequestDTO request) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudiante no encontrado"));

        Usuario usuario = buildUsuario(request.usuario(), false, estudiante.getUsuario());
        usuario = usuarioService.updateEntity(estudiante.getUsuario().getId(), usuario);

        estudiante.setUsuario(usuario);
        estudiante.setCodigoEstudiante(request.codigoEstudiante());
        estudiante.setEstadoAcademico(request.estadoAcademico() != null ? request.estadoAcademico() : estudiante.getEstadoAcademico());
        usuario.setEstudiante(estudiante);

        return toResponse(estudianteRepository.save(estudiante));
    }
    
    public void delete(Long id) {
        estudianteRepository.deleteById(id);
    }
    
    public boolean existsByCodigo(String codigo) {
        return estudianteRepository.existsByCodigoEstudiante(codigo);
    }

    private Usuario buildUsuario(UsuarioRequestDTO request, boolean creating, Usuario current) {
        if (request == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario es obligatorio");
        }

        Usuario usuario = current != null ? current : new Usuario();
        usuario.setNombres(request.getNombres());
        usuario.setApellidos(request.getApellidos());
        usuario.setEmail(request.getEmail());
        usuario.setEstado(request.getEstado() != null ? request.getEstado() : (usuario.getEstado() != null ? usuario.getEstado() : com.rodrigomv.edutrackbackend.persistence.enums.UsuarioEstado.ACTIVO));
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            usuario.setPasswordHash(request.getPassword());
        } else if (creating) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La contraseña del usuario es obligatoria");
        }
        return usuario;
    }

    private EstudianteResponseDTO toResponse(Estudiante estudiante) {
        return new EstudianteResponseDTO(
                estudiante.getId(),
                estudiante.getUsuario() != null ? new UsuarioResponseDTO(
                        estudiante.getUsuario().getId(),
                        estudiante.getUsuario().getNombres(),
                        estudiante.getUsuario().getApellidos(),
                        estudiante.getUsuario().getEmail(),
                        estudiante.getUsuario().getEstado(),
                        estudiante.getUsuario().getCreatedAt()
                ) : null,
                estudiante.getCodigoEstudiante(),
                estudiante.getEstadoAcademico()
        );
    }
}