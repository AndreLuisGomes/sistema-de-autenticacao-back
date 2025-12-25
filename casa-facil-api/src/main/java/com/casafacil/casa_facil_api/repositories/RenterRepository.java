package com.casafacil.casa_facil_api.repositories;

import com.casafacil.casa_facil_api.domain.user.Renter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RenterRepository extends JpaRepository<Renter, UUID> {

    Optional<Renter> findByEmail(String email);

}
