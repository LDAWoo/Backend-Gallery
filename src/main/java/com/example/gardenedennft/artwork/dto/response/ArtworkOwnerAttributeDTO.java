package com.example.gardenedennft.artwork.dto.response;

import com.example.gardenedennft.artwork.entity.Artwork;
import com.example.gardenedennft.attribute.dto.AttributeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtworkOwnerAttributeDTO {
    private List<Artwork> artworkList;
    private List<AttributeDTO> attributeDTOS;
}
