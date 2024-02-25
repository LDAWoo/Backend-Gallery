package com.example.gardenedennft.favoriteowner.service;

import com.example.gardenedennft.favoriteowner.dto.FavoriteOwnerDTO;
import com.example.gardenedennft.favoriteowner.dto.request.FavoriteOwnerRequest;
import com.example.gardenedennft.favoriteowner.entity.FavoriteOwner;

import java.util.List;
import java.util.UUID;

public interface FavoriteOwnerService {

    FavoriteOwnerDTO addFavoriteOwner(FavoriteOwnerRequest favoriteOwnerRequest);


    List<FavoriteOwnerDTO> FAVORITE_OWNER_DTO_LIST(UUID id_owner);


    Boolean existsOwnerById(UUID id);

    Boolean existFavoriteOwnerByWalletAddress(String Wallet_Address);

}
