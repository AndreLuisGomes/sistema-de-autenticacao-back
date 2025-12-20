package com.casafacil.casa_facil_api.infra.security;

import com.casafacil.casa_facil_api.domain.user.User;
import com.casafacil.casa_facil_api.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        return org.springframework.security.core.userdetails.User.
                withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities( new ArrayList<>())
                .build();
    }
}
