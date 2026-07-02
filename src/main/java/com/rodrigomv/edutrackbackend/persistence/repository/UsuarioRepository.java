package com.rodrigomv.edutrackbackend.persistence.repository;

import com.rodrigomv.edutrackbackend.persistence.entity.Usuario;
import com.rodrigomv.edutrackbackend.persistence.enums.UsuarioEstado;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    @EntityGraph(attributePaths = {"usuarioRoles", "usuarioRoles.rol", "docente"})
    @Query("select distinct u from Usuario u where lower(u.email) = lower(:email)")
    Optional<Usuario> findByEmail(@Param("email") String email);

    @EntityGraph(attributePaths = {"usuarioRoles", "usuarioRoles.rol", "docente"})
    @Query("select distinct u from Usuario u where lower(u.email) = lower(:email)")
    Optional<Usuario> findByEmailWithRoles(@Param("email") String email);
    
    boolean existsByEmail(String email);
    
    List<Usuario> findByEstado(UsuarioEstado estado);
}
