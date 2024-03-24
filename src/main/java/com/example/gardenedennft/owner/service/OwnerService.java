package com.example.gardenedennft.owner.service;

import com.example.gardenedennft.owner.dto.response.OwnerResponse;
import com.example.gardenedennft.owner.entity.Owner;
import com.example.gardenedennft.owner.dto.OwnerDTO;
import com.example.gardenedennft.owner.repo.OwnerRepo;

import java.util.List;
import java.util.UUID;

public interface OwnerService {
    Owner findOwnerById(UUID id);
    OwnerDTO findOwnerByWalletAddress(String walletAddress);
    void updateOwner(Owner owner);
    OwnerDTO findOwnerByIdArtwork(UUID id);
    List<OwnerDTO> findAllOwnerByIdArtist(UUID id);
    OwnerResponse findListOwnerByWalletAddress(String walletAddress);
}
