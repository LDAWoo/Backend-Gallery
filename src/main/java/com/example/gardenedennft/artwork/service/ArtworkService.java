package com.example.gardenedennft.artwork.service;

import com.example.gardenedennft.artist.entity.request.ArtistRequest;
import com.example.gardenedennft.artwork.dto.response.ArtworkTransactionAttributeDTO;
import com.example.gardenedennft.artwork.dto.ArtworkDTO;
import com.example.gardenedennft.artwork.entity.Artwork;
import com.example.gardenedennft.artwork.entity.request.*;
import com.example.gardenedennft.artwork.entity.response.ArtworkResponse;
import com.example.gardenedennft.transaction.TransactionRequest;

import java.util.List;
import java.util.UUID;

public interface ArtworkService {

    void createNFT(ArtistRequest artistRequest, ArtworkRequest request, TransactionRequest transactionRequest, List<String> categoryIds);
    void buyNFT(ArtworkBuyRequest request);
    ArtworkResponse findArtworkByWalletAddress(String walletAddress);
    ArtworkResponse findArtworkByWalletAddressAndByCondition(ArtworkArtistRequest request);
    ArtworkResponse findArtworkByEmailAndStatus(String email,Integer status);
    ArtworkResponse findArtworkByEmail(String email);
    List<ArtworkDTO> getAllArtworkByIdArtistAndStatus(UUID id, Integer status);
    ArtworkResponse findArtworkByIdArtistAndStatusAndCondition(ArtworkConditionRequest request);
    ArtworkResponse findArtworkByIdArtistAndStatus(UUID id, Integer status);
    ArtworkTransactionAttributeDTO findArtworkByIdAndEmail(Integer id, String email);
    Artwork findArtworkByEmailAndId(String email, Integer id);
    void updateArtworkById(Artwork artwork);

}
