package com.example.gardenedennft.artist.dto;

import com.example.gardenedennft.favoriteartist.dto.FavoriteArtistDTO;
import com.example.gardenedennft.owner.dto.OwnerDTO;
import com.example.gardenedennft.utils.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ArtistDTO extends BaseDTO {
    private String name;
    private String symbol;
    private String email;
    private String image_url;
    private String discord_url;
    private String twitter_url;
    private String telegram_url;
    private String website_url;
    private String bio;
    private String token;
    private Boolean status;

    private Double floorPrice;
    private Double buyNow;
    private Double sellNow;
    private Integer listed;
    private Integer supply;
    private Double percentListed;
    private Double totalPrice;
    private Double volume24h;
    private Double allVolume;
    private Double sales24h;
    private Double allSales;

    List<FavoriteArtistDTO> favoriteArtists;
    List<OwnerDTO> owners;
}
