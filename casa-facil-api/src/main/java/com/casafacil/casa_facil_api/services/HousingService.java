package com.casafacil.casa_facil_api.services;

import com.casafacil.casa_facil_api.repositories.HousingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HousingService {

    private final HousingRepository housingRepository;
}
