package com.example.gardenedennft.favoriteartwork.service.impl;

import com.example.gardenedennft.artwork.Artwork;
import com.example.gardenedennft.artwork.ArtworkResponse;
import com.example.gardenedennft.exception.ResourceNotFoundException;
import com.example.gardenedennft.favoriteartwork.dto.FavoriteArtWorkDTO;
import com.example.gardenedennft.favoriteartwork.dto.request.FavoriteArtworkRequest;
import com.example.gardenedennft.favoriteartwork.dto.response.FavoriteArtWorkResponse;
import com.example.gardenedennft.favoriteartwork.entity.FavoriteArtwork;
import com.example.gardenedennft.favoriteartwork.mapper.FavoriteArtWorkDTOMapper;
import com.example.gardenedennft.favoriteartwork.mapper.FavoriteListArtworkDTOMapper;
import com.example.gardenedennft.favoriteartwork.repo.FavoriteArtWorkRepo;
import com.example.gardenedennft.favoriteartwork.service.FavoriteArtWorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteArtWorkServiceImpl implements FavoriteArtWorkService {

    private final  FavoriteArtWorkRepo favoriteArtWorkRepo;

    private final ArtworkResponse artworkResponse;

    private final FavoriteListArtworkDTOMapper favoriteListArtworkDTOMapper;

    private final FavoriteArtWorkDTOMapper favoriteArtWorkDTOMapper;

    @Override
    public void addFavoriteArtWork(FavoriteArtworkRequest favoriteArtworkRequest) {
        String currentWalletAddress = favoriteArtworkRequest.getWalletAddress();
        Integer currentIdArtwork = favoriteArtworkRequest.getIdArtwork();

        FavoriteArtwork favoriteArtwork = findFavoriteArtWorkByWalletAddressAndIdArtwork(currentWalletAddress, currentIdArtwork);

        if(exitsFavoriteArtworkByWalletAddress(favoriteArtwork.getWallet_address())){
            favoriteArtwork.setStatus(!favoriteArtwork.getStatus());
            favoriteArtWorkRepo.save(favoriteArtwork);
        }else{
            Artwork artwork = artworkResponse.findById(currentIdArtwork).get();

            favoriteArtWorkRepo.save(
                    FavoriteArtwork.builder()
                            .status(true)
                            .artwork(artwork)
                            .wallet_address(currentWalletAddress)
                            .build()
            );
        }
    }

    @Override
    public FavoriteArtwork findFavoriteArtWorkByWalletAddressAndIdArtwork(String walletAddress, Integer idArtwork) {
        FavoriteArtwork favoriteArtwork = favoriteArtWorkRepo.findFavoriteArtWorkByWalletAddressAndIdArtwork(walletAddress, idArtwork)
                .orElseThrow(() -> new ResourceNotFoundException("Not found favoriteArtwork with walletAddress "+walletAddress+ "and id artwork "+idArtwork));

        return favoriteArtwork;
    }


    @Override
    public List<FavoriteArtWorkDTO> findListFavoriteArtworks(Integer idArtwork) {
        List<FavoriteArtwork> listfavoriteArtworks = favoriteArtWorkRepo.findListFavoriteArtworksByIdArtwork(idArtwork)
                .orElseThrow(() -> new ResourceNotFoundException("Not found favoriteArtwork with id artwork "+idArtwork));
        return favoriteListArtworkDTOMapper.apply(listfavoriteArtworks);

    }

    @Override
    public FavoriteArtWorkDTO findFavoriteArtwork(UUID id) {
       FavoriteArtwork favoriteArtwork = favoriteArtWorkRepo.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Not found favoriteArtwork with id "+id));

       return favoriteArtWorkDTOMapper.apply(favoriteArtwork);
    }

    @Override
    public Boolean exitsFavoriteArtworkByWalletAddress(String walletAddress) {
        return favoriteArtWorkRepo.existsFavoriteArtworkByWallet_address(walletAddress);
    }
}
