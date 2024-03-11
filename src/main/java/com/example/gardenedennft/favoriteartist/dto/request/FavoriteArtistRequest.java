package com.example.gardenedennft.favoriteartist.dto.request;

import lombok.*;

import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteArtistRequest {
    private String wallet_address;
    private UUID id_artist;
}
