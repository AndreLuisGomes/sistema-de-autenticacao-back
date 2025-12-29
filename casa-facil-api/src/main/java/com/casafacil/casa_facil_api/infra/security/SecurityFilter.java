package com.casafacil.casa_facil_api.infra.security;

import com.casafacil.casa_facil_api.models.owner.Owner;
import com.casafacil.casa_facil_api.models.renter.Renter;
import com.casafacil.casa_facil_api.repositories.OwnerRepository;
import com.casafacil.casa_facil_api.repositories.RenterRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    private final OwnerRepository ownerRepository;
    private final RenterRepository renterRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {
        var login = tokenService.validateToken(this.recoverToken(request));

        if(login != null){
            Owner owner = ownerRepository.findByEmail(login).orElseThrow(() -> new RuntimeException("User not found exception"));
            Renter renter = renterRepository.findByEmail(login).orElseThrow(()-> new RuntimeException("User not found exception"));

            if(owner.getEmail() != null){
                List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
                Authentication authentication = new UsernamePasswordAuthenticationToken(owner, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else{
                List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
                Authentication authentication = new UsernamePasswordAuthenticationToken(renter, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }

}
