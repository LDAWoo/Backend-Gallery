package com.example.gardenedennft.favoriteowner.service.Impl;

import com.example.gardenedennft.exception.ResourceNotFoundException;
import com.example.gardenedennft.favoriteowner.dto.FavoriteOwnerDTO;
import com.example.gardenedennft.favoriteowner.dto.request.FavoriteOwnerRequest;
import com.example.gardenedennft.favoriteowner.entity.FavoriteOwner;
import com.example.gardenedennft.favoriteowner.mapper.FavoriteOwnerDTOMapper;
import com.example.gardenedennft.favoriteowner.repo.FavoriteOwnerRepo;
import com.example.gardenedennft.favoriteowner.service.FavoriteOwnerService;
import com.example.gardenedennft.owner.Owner;
import com.example.gardenedennft.owner.OwnerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteOwnerServiceImpl implements FavoriteOwnerService {
    private final FavoriteOwnerRepo favoriteOwnerRepo;
    private final OwnerResponse ownerRepo;
    private final FavoriteOwnerDTOMapper favoriteOwnerDTOMapper;
    @Override
    public FavoriteOwnerDTO addFavoriteOwner(FavoriteOwnerRequest favoriteOwnerRequest) {
        UUID uuidOwner =favoriteOwnerRequest.getId_Owner();
        String Wallet_Address=favoriteOwnerRequest.getWallet_address();
       if (!existsOwnerById(uuidOwner)&&existFavoriteOwnerByWalletAddress(Wallet_Address)){
            throw new ResourceNotFoundException("Owner not found by id "+uuidOwner+" and FavoriteOwner by Wallet Address "+Wallet_Address);
       }
       FavoriteOwner favoriteOwner=favoriteOwnerRepo.findFavoriteOwnerByWallet_addressAndOwnerId(Wallet_Address,uuidOwner);

       if (favoriteOwner!=null){
            favoriteOwner.setStatus(!favoriteOwner.getStatus());
           return favoriteOwnerDTOMapper.apply(favoriteOwnerRepo.save(favoriteOwner));
       }
        FavoriteOwner save = favoriteOwnerRepo.save(
                FavoriteOwner.builder()
                        .wallet_address(favoriteOwner.getWallet_address())
                        .status(true)
                        .owner(favoriteOwner.getOwner())
                        .build()
        );
       return favoriteOwnerDTOMapper.apply(save);

    }

    @Override
    public List<FavoriteOwnerDTO> FAVORITE_OWNER_DTO_LIST(UUID id_owner) {
        List<FavoriteOwner> favoriteOwners=favoriteOwnerRepo.findFavoriteOwnersByOwnerId(id_owner)
                .orElseThrow(()->new NullPointerException("List null"));
        return favoriteOwners.stream()
                .map(favoriteOwnerDTOMapper)
                .toList();
    }

    @Override
    public Boolean existsOwnerById(UUID id) {
        return ownerRepo.existsById(id);
    }

    @Override
    public Boolean existFavoriteOwnerByWalletAddress(String Wallet_Address) {
        return favoriteOwnerRepo.existsFavoriteOwnerByWallet_address(Wallet_Address);
    }


    /*
     User click favoriteOwner get User Wallet Address

     String currentWalletAddress =

     Check:
        +Owner owner = findOwnerByWalletAddress(currentAddress);

        if owner.isPresent()

                continue -> Check
                Give me: currentWalletAddress, owner.id;

                check Database FavoriteOwner == function()

                FavoriteOwner favoriteOwner = findFavoriteOwnerByWalletAddressAndOwnerId(currentWalletAddress, owner.id);

                if(favoriteOwner != null){
                    // setStatus
                }else{
                    // save
                }


        else return Error wallet address;


    */
}
