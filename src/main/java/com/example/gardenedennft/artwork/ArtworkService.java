package com.example.gardenedennft.artwork;

import com.example.gardenedennft.artist.ArtistRequest;
import com.example.gardenedennft.transaction.TransactionRequest;

import java.util.List;
import java.util.UUID;

public interface ArtworkService {

    void createNFT(ArtistRequest artistRequest, ArtworkRequest request, TransactionRequest transactionRequest, List<String> categoryIds);
    ArtworkRepo findArtworkByWalletAddress(String walletAddress);
    ArtworkRepo findArtworkByEmailAndStatus(String email,Integer status);
    ArtworkRepo findArtworkByEmail(String email);
    ArtworkRepo findArtworkByIdOwnerAndStatus(UUID id, Integer status);
    ArtworkTransactionAttributeDTO findArtworkByIdAndEmail(Integer id, String email);
    Artwork findArtworkByEmailAndId(String email,Integer id);
    void updateNFT(Artwork artwork);

}
