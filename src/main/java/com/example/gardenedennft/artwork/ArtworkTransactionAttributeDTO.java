package com.example.gardenedennft.artwork;

import com.example.gardenedennft.attribute.AttributeDTO;
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
public class ArtworkTransactionAttributeDTO {
    private ArtworkDTO artwork;
    private TransactionDTO transaction;
    private List<AttributeDTO> attributes;
}


