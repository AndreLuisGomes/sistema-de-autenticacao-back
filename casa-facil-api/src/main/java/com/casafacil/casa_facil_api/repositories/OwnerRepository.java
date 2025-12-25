package com.casafacil.casa_facil_api.repositories;

import com.casafacil.casa_facil_api.domain.user.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OwnerRepository extends JpaRepository<Owner, UUID> {

    Optional<Owner> findByEmail(String email);

}
