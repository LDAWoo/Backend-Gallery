package com.example.gardenedennft.favoriteartwork.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteArtworkRequest {
    private String walletAddress;
    private Integer idArtwork;
}
