package com.example.gardenedennft.favoriteartwork.dto;

import com.example.gardenedennft.utils.BaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteArtWorkDTO {
    private String wallet_address;

    private Boolean status;

    private Integer id_artwork;
}
