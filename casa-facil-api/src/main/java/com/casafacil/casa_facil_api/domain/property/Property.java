package com.casafacil.casa_facil_api.domain.property;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
public class Property {

    private String name;

    private String desc;

    private double price;

    private UUID ownerId;
}
