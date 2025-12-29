package com.casafacil.casa_facil_api.models.owner;

import com.casafacil.casa_facil_api.models.property.Housing;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Owner{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String email;

    private String password;

    private String role;

    @OneToMany(mappedBy = "ownerId")
    private List<Housing> housings;

}
