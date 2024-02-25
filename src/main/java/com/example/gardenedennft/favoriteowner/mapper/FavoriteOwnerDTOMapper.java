package com.example.gardenedennft.favoriteowner.mapper;

import com.example.gardenedennft.favoriteowner.dto.FavoriteOwnerDTO;
import com.example.gardenedennft.favoriteowner.entity.FavoriteOwner;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class FavoriteOwnerDTOMapper implements Function<FavoriteOwner, FavoriteOwnerDTO> {
    private final ModelMapper modelMapper;
    @Override
    public FavoriteOwnerDTO apply(FavoriteOwner favoriteOwner) {
        return modelMapper.map(favoriteOwner,FavoriteOwnerDTO.class);
    }


}
