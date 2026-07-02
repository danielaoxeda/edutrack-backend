package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.dto.docente.DocenteRequestDTO;
import com.rodrigomv.edutrackbackend.dto.docente.DocenteResponseDTO;
import com.rodrigomv.edutrackbackend.dto.usuario.UsuarioRequestDTO;
import com.rodrigomv.edutrackbackend.dto.usuario.UsuarioResponseDTO;
import com.rodrigomv.edutrackbackend.persistence.entity.Docente;
import com.rodrigomv.edutrackbackend.persistence.entity.Usuario;
import com.rodrigomv.edutrackbackend.persistence.repository.DocenteRepository;
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
public class DocenteService {
    
    private final DocenteRepository docenteRepository;
    private final UsuarioService usuarioService;
    
    public List<DocenteResponseDTO> findAll() {
        return docenteRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }
    
    public Optional<DocenteResponseDTO> findById(Long id) {
        return docenteRepository.findById(id).map(this::toResponse);
    }
    
    public Optional<DocenteResponseDTO> findByCodigo(String codigo) {
        return docenteRepository.findByCodigoDocente(codigo).map(this::toResponse);
    }
    
    public DocenteResponseDTO save(DocenteRequestDTO request) {
        Usuario usuario = buildUsuario(request.usuario(), true, null);
        usuario = usuarioService.saveEntity(usuario);

        Docente docente = new Docente();
        docente.setUsuario(usuario);
        docente.setCodigoDocente(request.codigoDocente());
        docente.setEspecialidad(request.especialidad());
        usuario.setDocente(docente);

        return toResponse(docenteRepository.save(docente));
    }
    
    public DocenteResponseDTO update(Long id, DocenteRequestDTO request) {
        Docente docente = docenteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Docente no encontrado"));

        Usuario usuario = buildUsuario(request.usuario(), false, docente.getUsuario());
        usuario = usuarioService.updateEntity(docente.getUsuario().getId(), usuario);

        docente.setUsuario(usuario);
        docente.setCodigoDocente(request.codigoDocente());
        docente.setEspecialidad(request.especialidad());
        usuario.setDocente(docente);

        return toResponse(docenteRepository.save(docente));
    }
    
    public void delete(Long id) {
        docenteRepository.deleteById(id);
    }
    
    public boolean existsByCodigo(String codigo) {
        return docenteRepository.existsByCodigoDocente(codigo);
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

    private DocenteResponseDTO toResponse(Docente docente) {
        return new DocenteResponseDTO(
                docente.getId(),
                docente.getUsuario() != null ? new UsuarioResponseDTO(
                        docente.getUsuario().getId(),
                        docente.getUsuario().getNombres(),
                        docente.getUsuario().getApellidos(),
                        docente.getUsuario().getEmail(),
                        docente.getUsuario().getEstado(),
                        docente.getUsuario().getCreatedAt()
                ) : null,
                docente.getCodigoDocente(),
                docente.getEspecialidad()
        );
    }
}