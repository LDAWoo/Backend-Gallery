package com.example.gardenedennft.artwork;

import com.example.gardenedennft.attribute.AttributeDTO;
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
