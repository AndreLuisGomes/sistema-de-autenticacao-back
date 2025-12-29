package com.casafacil.casa_facil_api.models.property;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Housing{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String desc;

    private double price;

    @Column(name = "owner_id")
    private UUID ownerId;

    @Embedded
    private Address address;

}
