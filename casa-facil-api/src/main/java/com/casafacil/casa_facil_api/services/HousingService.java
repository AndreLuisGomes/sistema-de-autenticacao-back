package com.casafacil.casa_facil_api.services;

import com.casafacil.casa_facil_api.models.housing.Housing;
import com.casafacil.casa_facil_api.models.housing.HousingRequestDTO;
import com.casafacil.casa_facil_api.repositories.HousingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HousingService {

    private final HousingRepository housingRepository;

    public void saveHousing(HousingRequestDTO housing) throws IOException {
        Housing newHousing = housing.map(housing);
        newHousing.setProfileImageUrl(this.uploadImage(housing.profileImageUrl()));
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
                    unaccent(h.getTitle().toLowerCase().replaceAll("\\s", ""))
                    .equals(unaccent(housingName).toLowerCase().replaceAll("\\s", ""))
        ).toList();
    return !matchedList.isEmpty();
    }

    String unaccent(String s) {
        return java.text.Normalizer.normalize(s, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

//    public String uploadImage(MultipartFile file) throws IOException {
//        // Salva a imagem no filesystem ou S3 e retorna a URL
//        String fileName = UUID.randomUUID() + file.getOriginalFilename();
//        Path path = Paths.get("uploads/" + fileName);
//        Files.copy(file.getInputStream(), path);
//        return "/uploads/" + fileName; // ou URL do S3
//    }

    public String uploadImage(MultipartFile file) throws IOException{
        Path root = Paths.get("/uploads");
        if(!Files.exists(root)){
            Files.createFile(root);
        }

        String fileName = UUID.randomUUID().toString() + "_" + StringUtils.cleanPath(file.getOriginalFilename());

        Path destination = root.resolve(fileName);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return "/uploads" + fileName;
    }

}
