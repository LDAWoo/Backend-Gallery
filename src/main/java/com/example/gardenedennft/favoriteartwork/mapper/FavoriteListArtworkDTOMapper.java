package com.example.gardenedennft.favoriteartwork.mapper;


import com.example.gardenedennft.favoriteartwork.dto.FavoriteArtWorkDTO;
import com.example.gardenedennft.favoriteartwork.entity.FavoriteArtwork;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteListArtworkDTOMapper implements Function<List<FavoriteArtwork>, List<FavoriteArtWorkDTO>> {

    private final FavoriteArtWorkDTOMapper favoriteArtWorkDTOMapper;

    @Override
    public List<FavoriteArtWorkDTO> apply(List<FavoriteArtwork> favoriteArtworks) {
        return favoriteArtworks
                .stream()
                .map(this::mapToListDTO)
                .collect(Collectors.toList());
    }

    public FavoriteArtWorkDTO mapToListDTO (FavoriteArtwork favoriteArtwork){
        return favoriteArtWorkDTOMapper.apply(favoriteArtwork);
    }

}
