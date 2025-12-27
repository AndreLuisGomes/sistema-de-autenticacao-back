package com.casafacil.casa_facil_api.services;

import com.casafacil.casa_facil_api.domain.user.Owner;
import com.casafacil.casa_facil_api.dto.RegisterRequestDTO;
import com.casafacil.casa_facil_api.repositories.OwnerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<Owner> findOwnerByEmail(String email) {
        return this.ownerRepository.findByEmail(email);
    }

    public void saveOwner(RegisterRequestDTO request){
        Owner owner = new Owner();
        owner.setName(request.name());
        owner.setEmail(request.email());
        owner.setRole(request.role());
        owner.setPassword(this.passwordEncoder.encode(request.password()));
        owner.setHousings(new ArrayList<>());
        this.ownerRepository.save(owner);
    }

}
//    public Optional<User> findByEmail(String email){
//        return this.userRepository.findByEmail(email);
//    }
//
//    public void saveOwner(RegisterRequestDTO owner){
//        Owner newOwner = new Owner();
//        newOwner.setEmail(owner.email());
//        newOwner.setName(owner.name());
////        newOwner.setUserRole(owner.userRole());
//        newOwner.setPassword(owner.password());
//        this.ownerRepository.save(newOwner);
//    }
//
//    public void saveRenter(RegisterRequestDTO renter){
//        Renter newRenter = new Renter();
//        newRenter.setEmail(renter.email());
//        newRenter.setName(renter.name());
////        newRenter.setUserRole(renter.userRole());
//        newRenter.setPassword(renter.password());
//        this.renterRepository.save(newRenter);
//    }
//}
