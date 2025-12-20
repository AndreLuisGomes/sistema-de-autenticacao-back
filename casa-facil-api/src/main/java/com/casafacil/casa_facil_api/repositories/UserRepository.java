package com.casafacil.casa_facil_api.repositories;

import com.casafacil.casa_facil_api.domain.user.User;
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    List<User> findByName(String name);
}
