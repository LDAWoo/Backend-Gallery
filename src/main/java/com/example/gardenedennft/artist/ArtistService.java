package com.example.gardenedennft.artist;

import com.example.gardenedennft.artist.entity.request.ArtistConditionRequest;
import com.example.gardenedennft.artist.entity.request.ArtistRequest;

import java.util.UUID;

public interface ArtistService {
    Artist findArtistByEmail(String email);
    void authentication(ArtistAuthenticationRequest request);
    void artistSignIn(ArtistAuthenticationRequest request);
    Boolean existsArtistByEmail(String email);
    void updateStatusByEmail(String email);
    ArtistDTO confirmationToken(String token);
    Artist findArtistById(UUID id);
    void updateArtist (ArtistRequest artistRequest);
    ArtistRepo findAll();
    ArtistRepo findAllArtistsByTrending();
    ArtistRepo findAllArtistsByCondition(ArtistConditionRequest request);
    ArtistDTO findArtistByToken(String token);
    ArtistOwnerDTO findArtistBySymbol(String symbol);
}
