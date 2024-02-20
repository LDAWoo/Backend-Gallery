package com.example.gardenedennft.artist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArtistRequest {
    private UUID id_artist;
    private String email;
    private String name;
    private String symbol;
    private String image_url;
    private String discord_url;
    private String twitter_url;
    private String website_url;
    private String telegram_url;
    private String bio;
    private Boolean status;
}
