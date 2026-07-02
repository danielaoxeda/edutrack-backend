package com.rodrigomv.edutrackbackend.security;

import com.rodrigomv.edutrackbackend.persistence.entity.Usuario;
import com.rodrigomv.edutrackbackend.persistence.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        return new User(
                usuario.getEmail(),
                usuario.getPasswordHash(),
                usuario.getEstado().name().equals("ACTIVO"),
                true,
                true,
                true,
                mapRolesToAuthorities(usuario)
        );
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Usuario usuario) {
        return usuario.getUsuarioRoles().stream()
                .map(ur -> new SimpleGrantedAuthority("ROLE_" + ur.getRol().getNombre()))
                .collect(Collectors.toList());
    }
}
