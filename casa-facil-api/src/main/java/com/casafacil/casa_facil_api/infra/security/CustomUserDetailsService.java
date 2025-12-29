package com.casafacil.casa_facil_api.infra.security;

import com.casafacil.casa_facil_api.models.owner.Owner;
import com.casafacil.casa_facil_api.models.renter.Renter;
import com.casafacil.casa_facil_api.repositories.OwnerRepository;
import com.casafacil.casa_facil_api.repositories.RenterRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private OwnerRepository ownerRepository;
    private RenterRepository renterRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Owner owner = this.ownerRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        Renter renter = this.renterRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        if(owner.getEmail() != null){
            return org.springframework.security.core.userdetails.User.
                    withUsername(owner.getEmail())
                    .password(owner.getPassword())
                    .authorities( new ArrayList<>())
                    .build();
        }else {
            return org.springframework.security.core.userdetails.User.
                    withUsername(renter.getEmail())
                    .password(renter.getPassword())
                    .authorities( new ArrayList<>())
                    .build();
        }
    }
}
