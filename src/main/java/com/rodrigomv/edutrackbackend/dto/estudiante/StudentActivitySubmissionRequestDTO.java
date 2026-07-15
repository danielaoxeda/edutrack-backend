package com.rodrigomv.edutrackbackend.dto.estudiante;

import jakarta.validation.constraints.Size;

public record StudentActivitySubmissionRequestDTO(
        @Size(max = 1000) String comentarioAlumno,
        @Size(max = 255) String archivoUrl
) {
}
