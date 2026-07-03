package com.rodrigomv.edutrackbackend.service;

import com.rodrigomv.edutrackbackend.dto.actividad.ActividadRequestDTO;
import com.rodrigomv.edutrackbackend.dto.actividad.ActividadResponseDTO;
import com.rodrigomv.edutrackbackend.dto.docente.TeacherActivityOptionDTO;
import com.rodrigomv.edutrackbackend.dto.docente.TeacherActivityRequestDTO;
import com.rodrigomv.edutrackbackend.persistence.entity.Docente;
import com.rodrigomv.edutrackbackend.persistence.entity.Seccion;
import com.rodrigomv.edutrackbackend.persistence.entity.SemanaAcademica;
import com.rodrigomv.edutrackbackend.persistence.repository.SeccionRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.SemanaAcademicaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TeacherActivityService {

    private final CurrentTeacherService currentTeacherService;
    private final SeccionRepository seccionRepository;
    private final SemanaAcademicaRepository semanaAcademicaRepository;
    private final ActividadService actividadService;

    @Transactional(readOnly = true)
    public List<TeacherActivityOptionDTO> getAssignedSections() {
        Docente docente = currentTeacherService.getRequiredTeacher();
        Set<Long> sectionIds = currentTeacherService.getAssignedSectionIds(docente);

        return seccionRepository.findAll().stream()
                .filter(seccion -> sectionIds.contains(seccion.getId()))
                .sorted(Comparator.comparing(seccion -> seccion.getCurso().getNombre()))
                .map(seccion -> new TeacherActivityOptionDTO(
                        seccion.getId(),
                        seccion.getCurso().getCodigo(),
                        seccion.getCurso().getNombre(),
                        seccion.getNombre()
                ))
                .toList();
    }

    @Transactional
    public ActividadResponseDTO create(TeacherActivityRequestDTO request) {
        Docente docente = currentTeacherService.getRequiredTeacher();
        Set<Long> sectionIds = currentTeacherService.getAssignedSectionIds(docente);
        if (!sectionIds.contains(request.seccionId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "La seccion no esta asignada al docente autenticado");
        }
        if (!request.fechaLimite().isAfter(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha limite debe ser futura");
        }

        Seccion seccion = seccionRepository.findById(request.seccionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seccion no encontrada"));
        SemanaAcademica semana = semanaAcademicaRepository.findBySeccionIdOrderByNumeroSemanaAsc(seccion.getId()).stream()
                .filter(item -> item.getNumeroSemana().equals(request.numeroSemana()))
                .findFirst()
                .orElseGet(() -> createWeek(seccion, request.numeroSemana()));

        return actividadService.save(new ActividadRequestDTO(
                semana.getId(),
                null,
                request.titulo(),
                request.descripcion(),
                request.tipo(),
                request.fechaLimite(),
                request.calificada(),
                request.notaMaxima(),
                request.visible()
        ));
    }

    private SemanaAcademica createWeek(Seccion seccion, Integer weekNumber) {
        SemanaAcademica semana = new SemanaAcademica();
        semana.setSeccion(seccion);
        semana.setNumeroSemana(weekNumber);
        semana.setTitulo("Semana " + weekNumber);
        return semanaAcademicaRepository.save(semana);
    }
}
