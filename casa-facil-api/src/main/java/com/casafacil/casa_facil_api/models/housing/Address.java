package com.casafacil.casa_facil_api.models.housing;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    String street;
    String neighborhood;
    String city;
    String postalCode;
}
