package com.example.gardenedennft.favoriteartist.service;

import com.example.gardenedennft.favoriteartist.dto.FavoriteArtistDTO;
import com.example.gardenedennft.favoriteartist.dto.request.FavoriteArtistRequest;

import java.util.List;
import java.util.UUID;

public interface FavoriteArtistService {

    FavoriteArtistDTO addFavoriteArtist(FavoriteArtistRequest favoriteArtistRequest);


    List<FavoriteArtistDTO> findListFavoriteArtists(UUID id);


    Boolean existsArtistById(UUID id);

    Boolean existsFavoriteArtistByWalletAddress(String walletAddress);

}
