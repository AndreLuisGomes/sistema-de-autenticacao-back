package com.casafacil.casa_facil_api.domain.user;

import com.casafacil.casa_facil_api.domain.property.Housing;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Renter{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String email;

    private String password;

    private String role;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Housing> favorites;

}
