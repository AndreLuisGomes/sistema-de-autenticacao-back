package com.casafacil.casa_facil_api.services;

import com.casafacil.casa_facil_api.domain.user.Renter;
import com.casafacil.casa_facil_api.dto.RegisterRequestDTO;
import com.casafacil.casa_facil_api.repositories.RenterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RenterService{

    private final RenterRepository renterRepository;

    public void saveRenter(RegisterRequestDTO request){
        Renter renter = new Renter();
        renter.setName(request.name());
        renter.setRole(request.role());
        renter.setEmail(request.email());
        renter.setPassword(request.password());
        renter.setFavorites(new ArrayList<>());
        this.renterRepository.save(renter);
    }

    public Optional<Renter> findRenterByEmail(String email){
        return this.renterRepository.findByEmail(email);
    }

}
