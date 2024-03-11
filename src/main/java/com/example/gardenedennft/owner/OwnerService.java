package com.example.gardenedennft.owner;

import java.util.List;
import java.util.UUID;

public interface OwnerService {
    Owner findOwnerById(UUID id);
    OwnerDTO findOwnerByWalletAddress(String walletAddress);
    void updateOwner(Owner owner);
    OwnerDTO findOwnerByIdArtwork(UUID id);
    List<OwnerDTO> findAllOwnerByIdArtist(UUID id);
}
