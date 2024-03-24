package com.example.gardenedennft.artist.dto.response;

import com.example.gardenedennft.artwork.dto.ArtworkDTO;
import com.example.gardenedennft.utils.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ArtistResponse extends BaseDTO {
    private String name;
    private String symbol;
    private String email;
    private String image_url;
    private String discord_url;
    private String twitter_url;
    private String telegram_url;
    private String website_url;
    private String bio;
    private String token;
    private Boolean status;

    private List<ArtworkDTO> artworkDTOList;
}
