package com.example.gardenedennft.favoriteowner.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteOwnerDTO {
    private String wallet_address;
    private Boolean status;
    private UUID id_Owner;
}
