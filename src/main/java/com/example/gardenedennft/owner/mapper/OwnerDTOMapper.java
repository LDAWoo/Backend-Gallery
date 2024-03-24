package com.example.gardenedennft.owner.mapper;

import com.example.gardenedennft.owner.entity.Owner;
import com.example.gardenedennft.owner.dto.OwnerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class OwnerDTOMapper implements Function<Owner, OwnerDTO> {

    @Override
    public OwnerDTO apply(Owner owner) {
        OwnerDTO ownerDTO = OwnerDTO.builder()
                .id(owner.getId())
                .walletAddress(owner.getWallet_address())
                .email(owner.getArtist().getEmail())
                .name(owner.getArtist().getName())
                .symbol(owner.getArtist().getSymbol())
                .image_url(owner.getArtist().getImage_url())
                .discord_url(owner.getArtist().getDiscord_url())
                .twitter_url(owner.getArtist().getTwitter_url())
                .telegram_url(owner.getArtist().getTelegram_url())
                .website_url(owner.getArtist().getWebsite_url())
                .bio(owner.getArtist().getBio())
                .ownerStatus(owner.getStatus())
                .artistStatus(owner.getArtist().getStatus())
                .artworks(owner.getArtworks())
                .build();

        return ownerDTO;

    }
}
