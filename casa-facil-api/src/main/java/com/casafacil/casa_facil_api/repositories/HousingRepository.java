package com.casafacil.casa_facil_api.repositories;

import com.casafacil.casa_facil_api.domain.property.Housing;
import com.casafacil.casa_facil_api.domain.property.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface HousingRepository extends JpaRepository<Housing, UUID> {

    Optional<Housing> findByNameIgnoreCaseAndOwnerId(String name, UUID ownerId);

}
