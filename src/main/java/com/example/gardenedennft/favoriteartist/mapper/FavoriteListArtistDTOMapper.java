package com.example.gardenedennft.favoriteartist.mapper;


import com.example.gardenedennft.favoriteartist.dto.FavoriteArtistDTO;
import com.example.gardenedennft.favoriteartist.entity.FavoriteArtist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteListArtistDTOMapper implements Function<List<FavoriteArtist>, List<FavoriteArtistDTO>> {

    private final FavoriteArtistDTOMapper artistDTOMapper;

    @Override
    public List<FavoriteArtistDTO> apply(List<FavoriteArtist> favoriteArtists) {
        return favoriteArtists
                .stream()
                .map(this::mapToListDTO)
                .collect(Collectors.toList());
    }

    public FavoriteArtistDTO mapToListDTO (FavoriteArtist favoriteArtist){
        return artistDTOMapper.apply(favoriteArtist);
    }

}
