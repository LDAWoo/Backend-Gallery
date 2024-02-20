package com.example.gardenedennft.artwork;


import com.example.gardenedennft.artist.ArtistDTO;
import com.example.gardenedennft.attribute.AttributeDTO;
import com.example.gardenedennft.owner.OwnerDTO;
import com.example.gardenedennft.transaction.TransactionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArtworkListAttributeTransactionOwner {
    private ArtistDTO artist;
    private List<OwnerDTO> owners;
    private List<ArtworkDTO> artworks;
    private TransactionDTO transaction;
    private List<AttributeDTO> attributes;


}
