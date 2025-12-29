package com.casafacil.casa_facil_api.repositories;

import com.casafacil.casa_facil_api.models.property.Housing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HousingRepository extends JpaRepository<Housing, UUID> {

    @Query("SELECT h FROM Housing h WHERE REPLACE(LOWER(h.name), ' ', '') = :housingName AND h.ownerId = :ownerId")
    Optional<Housing> findByHousingNameAndOwnerId(@Param("housingName") String housingName, @Param("ownerId") UUID ownerId);

    List<Housing> findAllByOwnerId(UUID ownerId);
}
