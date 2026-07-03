package com.rodrigomv.edutrackbackend.config;

import com.rodrigomv.edutrackbackend.persistence.entity.Rol;
import com.rodrigomv.edutrackbackend.persistence.entity.Usuario;
import com.rodrigomv.edutrackbackend.persistence.entity.UsuarioRol;
import com.rodrigomv.edutrackbackend.persistence.enums.UsuarioEstado;
import com.rodrigomv.edutrackbackend.persistence.repository.RolRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.UsuarioRepository;
import com.rodrigomv.edutrackbackend.persistence.repository.UsuarioRolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminBootstrap implements ApplicationRunner {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.bootstrap.admin-email:}")
    private String email;

    @Value("${app.bootstrap.admin-password:}")
    private String password;

    @Value("${app.bootstrap.admin-first-name:Administrador}")
    private String firstName;

    @Value("${app.bootstrap.admin-last-name:EduTrack}")
    private String lastName;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (email == null || email.isBlank()) {
            return;
        }

        if (password.length() < 12) {
            throw new IllegalStateException("ADMIN_BOOTSTRAP_PASSWORD debe tener al menos 12 caracteres");
        }

        Rol adminRole = rolRepository.findByNombre("ADMIN")
                .orElseThrow(() -> new IllegalStateException("La migracion del rol ADMIN no fue aplicada"));

        Usuario admin = usuarioRepository.findByEmail(email.trim().toLowerCase())
                .map(this::syncAdmin)
                .orElseGet(this::createAdmin);

        if (!usuarioRolRepository.existsByUsuarioIdAndRolId(admin.getId(), adminRole.getId())) {
            UsuarioRol assignment = new UsuarioRol();
            assignment.setUsuario(admin);
            assignment.setRol(adminRole);
            usuarioRolRepository.save(assignment);
        }

        log.info("Administrador inicial verificado para {}", admin.getEmail());
    }

    private Usuario createAdmin() {
        Usuario admin = new Usuario();
        admin.setNombres(firstName.trim());
        admin.setApellidos(lastName.trim());
        admin.setEmail(email.trim().toLowerCase());
        admin.setPasswordHash(passwordEncoder.encode(password));
        admin.setEstado(UsuarioEstado.ACTIVO);
        admin.setCreatedAt(LocalDateTime.now());
        return usuarioRepository.save(admin);
    }

    private Usuario syncAdmin(Usuario admin) {
        admin.setNombres(firstName.trim());
        admin.setApellidos(lastName.trim());
        admin.setEmail(email.trim().toLowerCase());
        admin.setPasswordHash(passwordEncoder.encode(password));
        admin.setEstado(UsuarioEstado.ACTIVO);

        if (admin.getCreatedAt() == null) {
            admin.setCreatedAt(LocalDateTime.now());
        }

        return usuarioRepository.save(admin);
    }
}
