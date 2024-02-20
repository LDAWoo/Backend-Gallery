package com.example.gardenedennft.owner;

import com.example.gardenedennft.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService{

    private final OwnerResponse ownerResponse;

    private final OwnerDTOMapper ownerDTOMapper;

    private final OwnerListDTOMapper ownerListDTOMapper;

    @Override
    public Owner findOwnerById(UUID id) {
        return ownerResponse.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not  with id "+id));
    }

    @Override
    public OwnerDTO findOwnerByWalletAddress(String walletAddress) {
        Owner owner = ownerResponse.findOwnerByWalletAddress(walletAddress)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not  with wallet_address "+walletAddress));

        OwnerDTO ownerDTO = ownerDTOMapper.apply(owner);
        return ownerDTO;
    }

    @Override
    public void updateOwner(Owner owner) {
        ownerResponse.findOwnerByWalletAddress(owner.getWallet_address()).ifPresent(existingOwner -> {
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
            ownerResponse.save(existingOwner);
        });
    }

    @Override
    public OwnerDTO findOwnerByIdArtwork(UUID id) {
        Owner owner = ownerResponse.findOwnerByIdArtist(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not  with id artist "+id));

        OwnerDTO ownerDTO = ownerDTOMapper.apply(owner);
        return ownerDTO;
    }

    @Override
    public List<OwnerDTO> findAllOwnerByIdArtwork(UUID id) {
        List<Owner> owners = ownerResponse.findAllOwnerByIdArtwork(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not  with id artist "+id));

        List<OwnerDTO> ownerDTOList = ownerListDTOMapper.apply(owners);

        return ownerDTOList;
    }

}
