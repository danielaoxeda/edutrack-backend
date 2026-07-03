package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.dto.admin.AdminOverviewResponseDTO;
import com.rodrigomv.edutrackbackend.dto.admin.UserStatusRequestDTO;
import com.rodrigomv.edutrackbackend.dto.docente.DocenteRequestDTO;
import com.rodrigomv.edutrackbackend.dto.docente.DocenteResponseDTO;
import com.rodrigomv.edutrackbackend.dto.estudiante.EstudianteRequestDTO;
import com.rodrigomv.edutrackbackend.dto.estudiante.EstudianteResponseDTO;
import com.rodrigomv.edutrackbackend.dto.usuarioRol.UsuarioRolRequestDTO;
import com.rodrigomv.edutrackbackend.persistence.entity.Curso;
import com.rodrigomv.edutrackbackend.persistence.entity.Docente;
import com.rodrigomv.edutrackbackend.persistence.entity.DocenteSeccion;
import com.rodrigomv.edutrackbackend.persistence.entity.Estudiante;
import com.rodrigomv.edutrackbackend.persistence.entity.Matricula;
import com.rodrigomv.edutrackbackend.persistence.entity.PeriodoAcademico;
import com.rodrigomv.edutrackbackend.persistence.entity.Rol;
import com.rodrigomv.edutrackbackend.persistence.entity.Seccion;
import com.rodrigomv.edutrackbackend.persistence.entity.Usuario;
import com.rodrigomv.edutrackbackend.persistence.enums.UsuarioEstado;
import com.rodrigomv.edutrackbackend.persistence.repository.CursoRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.DocenteRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.DocenteSeccionRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.EstudianteRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.MatriculaRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.PeriodoAcademicoRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.RolRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.SeccionRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final UsuarioRepository usuarioRepository;
    private final DocenteRepository docenteRepository;
    private final EstudianteRepository estudianteRepository;
    private final CursoRepository cursoRepository;
    private final SeccionRepository seccionRepository;
    private final MatriculaRepository matriculaRepository;
    private final DocenteSeccionRepository docenteSeccionRepository;
    private final PeriodoAcademicoRepository periodoAcademicoRepository;
    private final RolRepository rolRepository;
    private final DocenteService docenteService;
    private final EstudianteService estudianteService;
    private final UsuarioRolService usuarioRolService;

    @Transactional(readOnly = true)
    public AdminOverviewResponseDTO getOverview() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<Docente> docentes = docenteRepository.findAll();
        List<Estudiante> estudiantes = estudianteRepository.findAll();
        List<Curso> cursos = cursoRepository.findAll();
        List<Seccion> secciones = seccionRepository.findAll();
        List<Matricula> matriculas = matriculaRepository.findAll();
        List<DocenteSeccion> docenteSecciones = docenteSeccionRepository.findAll();
        List<PeriodoAcademico> periodos = periodoAcademicoRepository.findAll();
        List<Rol> roles = rolRepository.findAll();

        AdminOverviewResponseDTO.Summary summary = new AdminOverviewResponseDTO.Summary(
                usuarios.size(),
                docentes.size(),
                estudiantes.size(),
                cursos.size(),
                secciones.size(),
                matriculas.size(),
                periodos.stream()
                        .filter(periodo -> periodo.getEstado() != null && periodo.getEstado().name().equalsIgnoreCase("ACTIVO"))
                        .map(PeriodoAcademico::getNombre)
                        .findFirst()
                        .orElse(null)
        );

        List<AdminOverviewResponseDTO.UserItem> users = usuarios.stream()
                .sorted(Comparator.comparing(Usuario::getCreatedAt, Comparator.nullsLast(Comparator.reverseOrder())))
                .map(usuario -> new AdminOverviewResponseDTO.UserItem(
                        usuario.getId(),
                        buildFullName(usuario.getNombres(), usuario.getApellidos()),
                        usuario.getEmail(),
                        usuario.getEstado(),
                        usuario.getCreatedAt(),
                        usuario.getUsuarioRoles().stream()
                                .map(usuarioRol -> usuarioRol.getRol() != null ? usuarioRol.getRol().getNombre() : null)
                                .filter(Objects::nonNull)
                                .distinct()
                                .sorted()
                                .toList(),
                        usuario.getDocente() != null ? usuario.getDocente().getId() : null,
                        usuario.getEstudiante() != null ? usuario.getEstudiante().getId() : null
                ))
                .toList();

        List<AdminOverviewResponseDTO.TeacherItem> teacherItems = docentes.stream()
                .sorted(Comparator.comparing((Docente docente) -> docente.getUsuario() != null ? docente.getUsuario().getNombres() : "", String.CASE_INSENSITIVE_ORDER))
                .map(docente -> new AdminOverviewResponseDTO.TeacherItem(
                        docente.getId(),
                        docente.getUsuario() != null ? buildFullName(docente.getUsuario().getNombres(), docente.getUsuario().getApellidos()) : docente.getCodigoDocente(),
                        docente.getUsuario() != null ? docente.getUsuario().getEmail() : null,
                        docente.getCodigoDocente(),
                        docente.getEspecialidad(),
                        docente.getUsuario() != null ? docente.getUsuario().getEstado() : null,
                        docente.getDocenteSecciones() != null ? docente.getDocenteSecciones().size() : 0
                ))
                .toList();

        List<AdminOverviewResponseDTO.StudentItem> studentItems = estudiantes.stream()
                .sorted(Comparator.comparing((Estudiante estudiante) -> estudiante.getUsuario() != null ? estudiante.getUsuario().getNombres() : "", String.CASE_INSENSITIVE_ORDER))
                .map(estudiante -> new AdminOverviewResponseDTO.StudentItem(
                        estudiante.getId(),
                        estudiante.getUsuario() != null ? buildFullName(estudiante.getUsuario().getNombres(), estudiante.getUsuario().getApellidos()) : estudiante.getCodigoEstudiante(),
                        estudiante.getUsuario() != null ? estudiante.getUsuario().getEmail() : null,
                        estudiante.getCodigoEstudiante(),
                        estudiante.getUsuario() != null ? estudiante.getUsuario().getEstado() : null,
                        estudiante.getEstadoAcademico(),
                        estudiante.getMatriculas() != null ? estudiante.getMatriculas().size() : 0
                ))
                .toList();

        List<AdminOverviewResponseDTO.CourseItem> courseItems = cursos.stream()
                .sorted(Comparator.comparing(Curso::getNombre, String.CASE_INSENSITIVE_ORDER))
                .map(curso -> {
                    List<Seccion> seccionesCurso = secciones.stream()
                            .filter(seccion -> seccion.getCurso() != null && Objects.equals(seccion.getCurso().getId(), curso.getId()))
                            .toList();
                    List<Long> seccionIds = seccionesCurso.stream().map(Seccion::getId).toList();
                    int totalMatriculas = (int) matriculas.stream()
                            .filter(matricula -> matricula.getSeccion() != null && seccionIds.contains(matricula.getSeccion().getId()))
                            .count();
                    List<String> docentesCurso = docenteSecciones.stream()
                            .filter(relacion -> relacion.getSeccion() != null && seccionIds.contains(relacion.getSeccion().getId()))
                            .map(relacion -> relacion.getDocente() != null && relacion.getDocente().getUsuario() != null
                                    ? buildFullName(relacion.getDocente().getUsuario().getNombres(), relacion.getDocente().getUsuario().getApellidos())
                                    : null)
                            .filter(Objects::nonNull)
                            .distinct()
                            .sorted(String.CASE_INSENSITIVE_ORDER)
                            .toList();

                    return new AdminOverviewResponseDTO.CourseItem(
                            curso.getId(),
                            curso.getCodigo(),
                            curso.getNombre(),
                            curso.getDescripcion(),
                            curso.getCreditos(),
                            seccionesCurso.size(),
                            totalMatriculas,
                            docentesCurso
                    );
                })
                .toList();

        List<AdminOverviewResponseDTO.SectionItem> sectionItems = secciones.stream()
                .sorted(Comparator.comparing(Seccion::getNombre, String.CASE_INSENSITIVE_ORDER))
                .map(seccion -> new AdminOverviewResponseDTO.SectionItem(
                        seccion.getId(),
                        seccion.getNombre(),
                        seccion.getCurso() != null ? seccion.getCurso().getId() : null,
                        seccion.getCurso() != null ? seccion.getCurso().getNombre() : null,
                        seccion.getCurso() != null ? seccion.getCurso().getCodigo() : null,
                        seccion.getPeriodoAcademico() != null ? seccion.getPeriodoAcademico().getId() : null,
                        seccion.getPeriodoAcademico() != null ? seccion.getPeriodoAcademico().getNombre() : null,
                        seccion.getCapacidad() != null ? seccion.getCapacidad() : 0,
                        seccion.getMatriculas() != null ? seccion.getMatriculas().size() : 0,
                        seccion.getDocenteSecciones().stream()
                                .map(relacion -> relacion.getDocente() != null && relacion.getDocente().getUsuario() != null
                                        ? buildFullName(relacion.getDocente().getUsuario().getNombres(), relacion.getDocente().getUsuario().getApellidos())
                                        : null)
                                .filter(Objects::nonNull)
                                .distinct()
                                .sorted(String.CASE_INSENSITIVE_ORDER)
                                .toList()
                ))
                .toList();

        List<AdminOverviewResponseDTO.PeriodItem> periodItems = periodos.stream()
                .sorted(Comparator.comparing(PeriodoAcademico::getFechaInicio, Comparator.nullsLast(Comparator.reverseOrder())))
                .map(periodo -> new AdminOverviewResponseDTO.PeriodItem(
                        periodo.getId(),
                        periodo.getNombre(),
                        periodo.getFechaInicio(),
                        periodo.getFechaFin(),
                        periodo.getEstado()
                ))
                .toList();

        List<AdminOverviewResponseDTO.RoleItem> roleItems = roles.stream()
                .sorted(Comparator.comparing(Rol::getNombre, String.CASE_INSENSITIVE_ORDER))
                .map(rol -> new AdminOverviewResponseDTO.RoleItem(rol.getId(), rol.getNombre()))
                .toList();

        return new AdminOverviewResponseDTO(
                summary,
                users,
                teacherItems,
                studentItems,
                courseItems,
                sectionItems,
                periodItems,
                roleItems
        );
    }

    public DocenteResponseDTO createTeacher(DocenteRequestDTO request) {
        DocenteResponseDTO docente = docenteService.save(request);
        if (docente.usuario() != null && docente.usuario().getId() != null) {
            ensureRole(docente.usuario().getId(), "DOCENTE");
        }
        return docente;
    }

    public EstudianteResponseDTO createStudent(EstudianteRequestDTO request) {
        EstudianteResponseDTO estudiante = estudianteService.save(request);
        if (estudiante.usuario() != null && estudiante.usuario().getId() != null) {
            ensureRole(estudiante.usuario().getId(), "ESTUDIANTE");
        }
        return estudiante;
    }

    public void updateTeacherStatus(Long docenteId, UserStatusRequestDTO request) {
        Docente docente = docenteRepository.findById(docenteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profesor no encontrado"));
        validateSupportedStatus(request.estado());

        if (request.estado() == UsuarioEstado.INACTIVO && docenteSeccionRepository.existsByDocenteId(docenteId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se puede deshabilitar al profesor porque tiene secciones asignadas");
        }

        updateUserStatus(docente.getUsuario(), request.estado());
    }

    public void updateStudentStatus(Long estudianteId, UserStatusRequestDTO request) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alumno no encontrado"));
        validateSupportedStatus(request.estado());

        if (request.estado() == UsuarioEstado.INACTIVO && matriculaRepository.existsByEstudianteId(estudianteId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se puede deshabilitar al alumno porque tiene matriculas registradas");
        }

        updateUserStatus(estudiante.getUsuario(), request.estado());
    }

    private void validateSupportedStatus(UsuarioEstado estado) {
        if (estado != UsuarioEstado.ACTIVO && estado != UsuarioEstado.INACTIVO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estado debe ser ACTIVO o INACTIVO");
        }
    }

    private void updateUserStatus(Usuario usuario, UsuarioEstado estado) {
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El perfil no tiene una cuenta asociada");
        }
        usuario.setEstado(estado);
        usuarioRepository.save(usuario);
    }

    private void ensureRole(Long usuarioId, String roleName) {
        Rol rol = rolRepository.findByNombre(roleName.toUpperCase(Locale.ROOT))
                .or(() -> rolRepository.findByNombre(roleName))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado: " + roleName));

        if (!usuarioRolService.existsByUsuarioAndRol(usuarioId, rol.getId())) {
            usuarioRolService.save(new UsuarioRolRequestDTO(usuarioId, rol.getId()));
        }
    }

    private String buildFullName(String nombres, String apellidos) {
        return (nombres == null ? "" : nombres).trim() + " " + (apellidos == null ? "" : apellidos).trim();
    }
}
