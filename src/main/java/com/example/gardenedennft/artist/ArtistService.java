package com.example.gardenedennft.artist;

import com.example.gardenedennft.owner.Owner;

import java.util.UUID;

public interface ArtistService {
    Artist findArtistByEmail(String email);
    void authentication(ArtistAuthenticationRequest request);
    Boolean existsArtistByEmail(String email);
    void updateStatusByEmail(String email);
    ArtistDTO confirmationToken(String token);
    Artist findArtistById(UUID id);
    void updateArtist (Artist artist);
    ArtistRepo findAll();
    ArtistDTO findArtistByToken(String token);
    ArtistOwnerDTO findArtistBySymbol(String symbol);
}
