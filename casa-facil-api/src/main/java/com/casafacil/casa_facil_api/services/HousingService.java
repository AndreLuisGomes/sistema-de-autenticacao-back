package com.casafacil.casa_facil_api.services;

import com.casafacil.casa_facil_api.models.property.Housing;
import com.casafacil.casa_facil_api.models.property.HousingRequestDTO;
import com.casafacil.casa_facil_api.repositories.HousingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HousingService {

    private final HousingRepository housingRepository;

    public void saveHousing(HousingRequestDTO housing){
        Housing newHousing = new Housing();
        newHousing.setName(housing.name());
        newHousing.setDesc(housing.desc());
        newHousing.setAddress(housing.address());
        newHousing.setOwnerId(housing.ownerId());
        newHousing.setPrice(housing.price());
        this.housingRepository.save(newHousing);
    }

    public List<Housing> findAll(){
        return this.housingRepository.findAll();
    }

    public boolean compareByHousingNameAndOwnerId(String housingName, UUID ownerId){
        List<Housing> returnedList = this.housingRepository.findAllByOwnerId(ownerId);
        System.out.println(returnedList);
        List<Housing> matchedList = returnedList
                .stream()
                .filter(h ->
                    unaccent(h.getName().toLowerCase().replaceAll("\\s", ""))
                    .equals(unaccent(housingName).toLowerCase().replaceAll("\\s", ""))
        ).toList();
    return !matchedList.isEmpty();
    }

    String unaccent(String s) {
        return java.text.Normalizer.normalize(s, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}
