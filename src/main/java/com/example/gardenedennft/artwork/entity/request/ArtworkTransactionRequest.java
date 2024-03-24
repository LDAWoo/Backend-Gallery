package com.example.gardenedennft.artwork.entity.request;

import com.example.gardenedennft.artist.entity.request.ArtistRequest;
import com.example.gardenedennft.artwork.entity.request.ArtworkRequest;
import com.example.gardenedennft.transaction.TransactionRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtworkTransactionRequest {
    private ArtistRequest artistRequest;
    private ArtworkRequest artworkRequest;
    private TransactionRequest transactionRequest;
    private List<String> categoryIds;
}
