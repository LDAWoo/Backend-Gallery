package com.example.gardenedennft.favoriteartist.dto;

import com.example.gardenedennft.utils.BaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteArtistDTO extends BaseDTO {
    private String wallet_address;
    private Boolean status;
    private UUID id_artist;
}
