package com.casafacil.casa_facil_api.models.housing;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

// Classe que espelha a requisição vinda do front-end //

public record HousingRequestDTO(MultipartFile profileImageUrl, String title, String number, String desc, double price, Address address, UUID ownerId) {

    // Método para mapear entidade de requisição para Housing //

    public Housing map(HousingRequestDTO housingDTO){
        Housing housing = new Housing();
        housing.setTitle(housingDTO.title);
        housing.setNumber(housingDTO.number);
        housing.setDesc(housingDTO.desc);
        housing.setPrice(housingDTO.price);
        housing.setAddress(housingDTO.address);
        housing.setOwnerId(housingDTO.ownerId);
        return housing;
    }
}
