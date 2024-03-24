package com.example.gardenedennft.artwork.entity.request;


import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArtworkArtistRequest {
    private String symbol;
    private String walletAddress;
}
