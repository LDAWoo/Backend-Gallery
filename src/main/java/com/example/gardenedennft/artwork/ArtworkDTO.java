package com.example.gardenedennft.artwork;

import com.example.gardenedennft.artist.ArtistDTO;
import com.example.gardenedennft.attribute.AttributeDTO;
import com.example.gardenedennft.favoriteartwork.dto.FavoriteArtWorkDTO;
import com.example.gardenedennft.owner.OwnerDTO;
import com.example.gardenedennft.transaction.TransactionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArtworkDTO {
    private Integer id;
    private String name;
    private String symbol;
    private String description;
    private String image_url;
    private String wallet_address;
    private Double price;
    private Integer supply;
    private Integer royalty;
    private Integer minted;
    private Date minted_date;
    private Integer status;

    private ArtistDTO artist;
    private OwnerDTO owner;
    private TransactionDTO transaction;
    private List<AttributeDTO> attributes;
    private List<FavoriteArtWorkDTO> favoriteArtWorks;
}
