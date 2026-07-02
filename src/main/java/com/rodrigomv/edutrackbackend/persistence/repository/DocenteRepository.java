package com.rodrigomv.edutrackbackend.persistence.repository;

import com.rodrigomv.edutrackbackend.persistence.entity.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Long> {
    
    Optional<Docente> findByCodigoDocente(String codigoDocente);

    Optional<Docente> findByUsuarioId(Long usuarioId);
    
    boolean existsByCodigoDocente(String codigoDocente);

    boolean existsByUsuarioId(Long usuarioId);
}
