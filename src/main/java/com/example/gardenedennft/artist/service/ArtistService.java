package com.example.gardenedennft.artist.service;

import com.example.gardenedennft.artist.entity.response.ArtistResponse;
import com.example.gardenedennft.artist.dto.ArtistDTO;
import com.example.gardenedennft.artist.entity.Artist;
import com.example.gardenedennft.artist.entity.request.ArtistAuthenticationRequest;
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
    ArtistResponse findAll();
    ArtistResponse findAllArtistsByTrending();
    ArtistResponse findAllArtistsByCondition(ArtistConditionRequest request);
    ArtistDTO findArtistByToken(String token);
    ArtistDTO findArtistBySymbol(String symbol);
}
