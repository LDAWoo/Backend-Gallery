package com.example.gardenedennft.owner.dto;


import com.example.gardenedennft.artwork.dto.ArtworkDTO;
import com.example.gardenedennft.artwork.entity.Artwork;
import com.example.gardenedennft.utils.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OwnerDTO{
    private UUID id;
    private String walletAddress;
    private String email;
    private String name;
    private String symbol;
    private String image_url;
    private String discord_url;
    private String twitter_url;
    private String telegram_url;
    private String website_url;
    private String bio;
    private Boolean ownerStatus;
    private Boolean artistStatus;

    private Double floorPrice;
    private Integer listed;
    private Integer supply;
    private Double totalPrice;
    private Integer totalRoyalty;

    private List<Artwork> artworks;
}
