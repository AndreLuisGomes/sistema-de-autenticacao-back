package com.casafacil.casa_facil_api.controllers;

import com.casafacil.casa_facil_api.models.owner.Owner;
import com.casafacil.casa_facil_api.models.housing.HousingRequestDTO;
import com.casafacil.casa_facil_api.repositories.HousingRepository;
import com.casafacil.casa_facil_api.repositories.OwnerRepository;
import com.casafacil.casa_facil_api.services.HousingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/properties")
@AllArgsConstructor
public class HousingController {

    private final OwnerRepository ownerRepository;
    private final HousingRepository housingRepository;
    private final HousingService housingService;

    @PostMapping("/save")
    public ResponseEntity saveHousing(@RequestBody HousingRequestDTO body){

        // Verifica se o UUID passado pela requisição realmente existe:
        try{
            Optional<Owner> owner = this.ownerRepository.findById(body.ownerId());
            // Se ele existe então o que está dentro do "if" acontece:
            if(owner.isPresent()){
                // Verifica se existe alguma propriedade com o nome da propriedade e o id do proprietario:
                if(housingService.compareByHousingNameAndOwnerId(body.title(), body.ownerId())){
                    return ResponseEntity.badRequest().body("Já existe uma propriedade sua com esse nome!");
                }
                if(!body.profileImageUrl().isEmpty()){
                    this.housingService.saveHousing(body);
                    return ResponseEntity.ok().build();
                }
                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.badRequest().body("O usuário passado não é um proprietário!");
            }
        }catch (HttpMessageNotReadableException e){
            return ResponseEntity.badRequest().body("O usuário não existe!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
