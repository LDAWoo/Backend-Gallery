package com.example.gardenedennft.owner.service.Impl;

import com.example.gardenedennft.artwork.dto.ArtworkDTO;
import com.example.gardenedennft.artwork.entity.Artwork;
import com.example.gardenedennft.artwork.mapper.ArtworkListDTOMapper;
import com.example.gardenedennft.artwork.repo.ArtworkRepo;
import com.example.gardenedennft.constant.SystemConstant;
import com.example.gardenedennft.exception.ResourceNotFoundException;
import com.example.gardenedennft.owner.dto.response.OwnerResponse;
import com.example.gardenedennft.owner.entity.Owner;
import com.example.gardenedennft.owner.dto.OwnerDTO;
import com.example.gardenedennft.owner.mapper.OwnerDTOMapper;
import com.example.gardenedennft.owner.mapper.OwnerListDTOMapper;
import com.example.gardenedennft.owner.repo.OwnerRepo;
import com.example.gardenedennft.owner.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final ArtworkRepo artworkRepo;

    private final ArtworkListDTOMapper artworkListDTOMapper;

    private final OwnerRepo ownerRepo;

    private final OwnerDTOMapper ownerDTOMapper;

    private final OwnerListDTOMapper ownerListDTOMapper;

    @Override
    public Owner findOwnerById(UUID id) {
        return ownerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not  with id "+id));
    }

    @Override
    public List<OwnerDTO> findOwnerByWalletAddress(String walletAddress) {
        List<Owner> owners = ownerRepo.findOwnerByWalletAddress(walletAddress)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not with wallet_address "+walletAddress));
        return ownerListDTOMapper.apply(owners);
    }

    @Override
    public void updateOwner(Owner owner) {
        ownerRepo.findById(owner.getId()).ifPresent(existingOwner -> {
            Field[] fields = Owner.class.getDeclaredFields();

            for (Field field: fields) {
                try{
                    field.setAccessible(true);
                    Object newValue = field.get(owner);
                    if(newValue != null){
                        field.set(existingOwner, newValue);
                    }
                }catch (IllegalAccessException e){
                    e.printStackTrace();
                }
            }
            existingOwner.setLastModifiedDate(new Date());
            ownerRepo.save(existingOwner);
        });
    }

    @Override
    public OwnerDTO findOwnerByIdArtwork(UUID id) {
        Owner owner = ownerRepo.findOwnerByIdArtist(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not  with id artist "+id));
        return ownerDTOMapper.apply(owner);
    }

    @Override
    public List<OwnerDTO> findAllOwnerByIdArtist(UUID id) {
        List<Owner> owners = ownerRepo.findAllOwnerByIdArtist(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not  with id artist "+id));
        return ownerListDTOMapper.apply(owners);
    }

    @Override
    public OwnerResponse findListOwnerByWalletAddress(String walletAddress) {
        List<Owner> owners = ownerRepo.findListOwnerByWalletAddress(walletAddress)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not  with wallet address "+walletAddress));

        List<OwnerDTO> ownerDTOList = ownerListDTOMapper.apply(owners);

         double floorPrice = 0.0;
         int listed = 0;
         int supply = 0;
         double totalPrice = 0.0;
         int totalRoyalty = 0;

        for(OwnerDTO ownerDTO: ownerDTOList){
            for(Artwork artwork: ownerDTO.getArtworks()){
                if (artwork.getPrice() != null) {
                    totalPrice += artwork.getPrice();
                    listed++;
                }

                if(artwork.getSupply() != null){
                    supply += artwork.getSupply();
                }

                if(artwork.getRoyalty() != null){
                    totalRoyalty += artwork.getRoyalty();
                }
            }

            if(listed != 0){
                floorPrice = Math.round((totalPrice / listed) * 100.0) / 100.0;
            }

            ownerDTO.setFloorPrice(floorPrice);
            ownerDTO.setSupply(supply);
            ownerDTO.setListed(listed);
            ownerDTO.setTotalPrice(totalPrice);
            ownerDTO.setTotalRoyalty(totalRoyalty);
        }

        return OwnerResponse.builder()
                .listResult(ownerDTOList
                ).build();
    }

}
