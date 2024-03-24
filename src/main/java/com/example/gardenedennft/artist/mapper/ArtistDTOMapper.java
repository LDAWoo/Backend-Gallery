package com.example.gardenedennft.artist.mapper;

import com.example.gardenedennft.artist.dto.ArtistDTO;
import com.example.gardenedennft.artist.entity.Artist;
import com.example.gardenedennft.token.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtistDTOMapper implements Function<Artist, ArtistDTO> {

    @Override
    public ArtistDTO apply(Artist artist) {
        ArtistDTO artistDTO = ArtistDTO.builder()
                .id(artist.getId())
                .createdBy(artist.getCreateBy())
                .createdDate(artist.getCreatedDate())
                .lastModifiedBy(artist.getLastModifiedBy())
                .lastModifiedDate(artist.getLastModifiedDate())
                .email(artist.getEmail())
                .name(artist.getName())
                .symbol(artist.getSymbol())
                .website_url(artist.getWebsite_url())
                .bio(artist.getBio())
                .discord_url(artist.getDiscord_url())
                .image_url(artist.getImage_url())
                .telegram_url(artist.getTelegram_url())
                .twitter_url(artist.getTwitter_url())
                .status(artist.getStatus())
                .token(artist.getTokens().stream()
                        .filter(t -> t.getArtist().getId() == artist.getId())
                        .map(Token::getToken)
                        .collect(Collectors.joining(","))
                )
                .build();

        return artistDTO;
    }
}
