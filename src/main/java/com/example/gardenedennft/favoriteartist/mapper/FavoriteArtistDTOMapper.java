package com.example.gardenedennft.favoriteartist.mapper;

import com.example.gardenedennft.favoriteartist.dto.FavoriteArtistDTO;
import com.example.gardenedennft.favoriteartist.entity.FavoriteArtist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class FavoriteArtistDTOMapper implements Function<FavoriteArtist, FavoriteArtistDTO> {

    @Override
    public FavoriteArtistDTO apply(FavoriteArtist favoriteArtist) {
        FavoriteArtistDTO favoriteOwnerDTO = FavoriteArtistDTO.builder()
                .id(favoriteArtist.getId())
                .id_artist(favoriteArtist.getArtist().getId())
                .wallet_address(favoriteArtist.getWallet_address())
                .status(favoriteArtist.getStatus())
                .build();
        return favoriteOwnerDTO;
    }


}
