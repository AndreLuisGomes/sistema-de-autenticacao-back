package com.casafacil.casa_facil_api.models.property;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    String street;
    String neighborhood;
    String city;
    String postalCode;
}
