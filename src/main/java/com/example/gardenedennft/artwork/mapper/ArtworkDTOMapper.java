package com.example.gardenedennft.artwork.mapper;

import com.example.gardenedennft.artwork.dto.ArtworkDTO;
import com.example.gardenedennft.artwork.entity.Artwork;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ArtworkDTOMapper implements Function<Artwork, ArtworkDTO> {
    @Override
    public ArtworkDTO apply(Artwork artwork) {
        ArtworkDTO artworkDTO = ArtworkDTO.builder()
                .id(artwork.getId())
                .name(artwork.getName())
                .symbol(artwork.getSymbol())
                .price(artwork.getPrice())
                .royalty(artwork.getRoyalty())
                .image_url(artwork.getImage_url())
                .description(artwork.getDescription())
                .supply(artwork.getSupply())
                .minted(artwork.getMinted())
                .wallet_address(artwork.getWallet_address())
                .chain(artwork.getChain())
                .status(artwork.getStatus())
                .minted_date(artwork.getMinted_date())
                .build();
        return artworkDTO;
    }
}
