package com.casafacil.casa_facil_api.models.property;

import java.util.UUID;

public record HousingRequestDTO(String name, String desc, double price, String address, UUID ownerId) {
    public Housing map(HousingRequestDTO housingDTO){
        Housing housing = new Housing();
        housing.setName(housingDTO.name);
        housing.setDesc(housingDTO.desc);
        housing.setPrice(housingDTO.price);
        housing.setAddress(housingDTO.address);
        housing.setOwnerId(housingDTO.ownerId);
        return housing;
    }
}
