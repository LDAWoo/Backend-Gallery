package com.example.gardenedennft.favoriteartwork.mapper;

import com.example.gardenedennft.favoriteartwork.dto.FavoriteArtWorkDTO;
import com.example.gardenedennft.favoriteartwork.entity.FavoriteArtwork;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class FavoriteArtWorkDTOMapper implements Function<FavoriteArtwork, FavoriteArtWorkDTO> {
        private  final ModelMapper mapper;
    @Override
    public FavoriteArtWorkDTO apply(FavoriteArtwork favoriteArtwork) {
<<<<<<< Updated upstream
        return mapper.map(favoriteArtwork,FavoriteArtWorkDTO.class);
=======
        FavoriteArtWorkDTO favoriteArtWorkDTO = FavoriteArtWorkDTO.builder()
                .id_artwork(favoriteArtwork.getArtwork().getId())
                .wallet_address(favoriteArtwork.getWallet_address())
                .status(favoriteArtwork.getStatus())
                .build();

        return favoriteArtWorkDTO;
>>>>>>> Stashed changes

    }
}
