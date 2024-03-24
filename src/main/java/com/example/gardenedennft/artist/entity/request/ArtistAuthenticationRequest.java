package com.example.gardenedennft.artist.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ArtistAuthenticationRequest {
    private String email;
    private String walletAddress;
}
