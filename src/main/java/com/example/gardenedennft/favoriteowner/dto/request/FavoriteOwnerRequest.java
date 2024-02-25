package com.example.gardenedennft.favoriteowner.dto.request;

import lombok.*;

import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteOwnerRequest {
    private String wallet_address;
    private UUID id_Owner;
}
