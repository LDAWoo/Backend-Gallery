package com.example.gardenedennft.artwork;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtworkListDTOMapper implements Function<List<Artwork>, List<ArtworkDTO>> {

    private final ArtworkDTOMapper artworkDTOMapper;

    @Override
    public List<ArtworkDTO> apply(List<Artwork> artworks) {
        return artworks.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ArtworkDTO mapToDTO (Artwork artwork){
        ArtworkDTO artworkDTO = artworkDTOMapper.apply(artwork);
        return artworkDTO;
    }
}
