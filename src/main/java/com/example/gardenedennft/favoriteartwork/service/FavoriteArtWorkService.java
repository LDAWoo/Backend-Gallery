package com.example.gardenedennft.favoriteartwork.service;

import com.example.gardenedennft.favoriteartwork.dto.FavoriteArtWorkDTO;
import com.example.gardenedennft.favoriteartwork.dto.request.FavoriteArtworkRequest;
import com.example.gardenedennft.favoriteartwork.entity.FavoriteArtwork;

import java.util.List;
import java.util.UUID;

public interface FavoriteArtWorkService {

    void addFavoriteArtWork(FavoriteArtworkRequest favoriteArtworkRequest);

    FavoriteArtwork findFavoriteArtWorkByWalletAddressAndIdArtwork(String walletAddress, Integer idArtwork);

    List<FavoriteArtWorkDTO> findListFavoriteArtworks(Integer idArtwork);

    FavoriteArtWorkDTO findFavoriteArtwork(UUID id);

    Boolean exitsFavoriteArtworkByWalletAddress(String walletAddress);

}
