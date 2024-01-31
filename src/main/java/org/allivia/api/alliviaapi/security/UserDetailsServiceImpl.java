package org.allivia.api.alliviaapi.security;

import org.allivia.api.alliviaapi.entities.AppUsuarioEntity;
import org.allivia.api.alliviaapi.repositories.IUserRepository;
import org.allivia.api.alliviaapi.services.NotificationMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private IUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUsuarioEntity> usuario = Optional.ofNullable(userRepository.findByUsuario(username));
        if (!usuario.isPresent()) {
            throw new UsernameNotFoundException(username);
        }

        return new User(usuario.get().getUsuario(), usuario.get().getPinPassword(), emptyList());
    }
}
