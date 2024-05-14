package com.example.gardenedennft.artwork.entity.request;

import com.example.gardenedennft.attribute.entity.Attribute;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ArtworkBuyRequest {
    private UUID id_artist;
    private String seller_address;
    private String buyer_address;
    private String tokenAddress;
    private String name;
    private String symbolNFT;
    private String description;
    private String image_url;
    private String chain;
    private Double amount;
    private Double price;

    private String signature;
    private List<String> categories;
    private List<Attribute> attributes;
}
