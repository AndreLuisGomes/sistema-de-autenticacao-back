package com.casafacil.casa_facil_api.models.housing;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Housing{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String profileImageUrl;

    private String title;

    private String number;

    private String desc;

    private double price;

    @Column(name = "owner_id")
    private UUID ownerId;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "housing", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<HousingImage> housingImageList;
}
