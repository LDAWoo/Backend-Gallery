package com.example.gardenedennft.favoriteartwork.mapper;

import com.example.gardenedennft.favoriteartwork.dto.FavoriteArtWorkDTO;
import com.example.gardenedennft.favoriteartwork.entity.FavoriteArtwork;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class FavoriteArtWorkDTOMapper implements Function<FavoriteArtwork, FavoriteArtWorkDTO> {

    @Override
    public FavoriteArtWorkDTO apply(FavoriteArtwork favoriteArtwork) {
        return new FavoriteArtWorkDTO(
                favoriteArtwork.getWallet_address(),
                favoriteArtwork.getStatus(),
                favoriteArtwork.getArtwork().getId()
                );

    }
}
