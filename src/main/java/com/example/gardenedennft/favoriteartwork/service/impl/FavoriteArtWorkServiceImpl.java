package com.example.gardenedennft.favoriteartwork.service.impl;

import com.example.gardenedennft.artwork.entity.Artwork;
import com.example.gardenedennft.artwork.repo.ArtworkRepo;
import com.example.gardenedennft.exception.ResourceNotFoundException;
import com.example.gardenedennft.favoriteartwork.dto.FavoriteArtWorkDTO;
import com.example.gardenedennft.favoriteartwork.dto.request.FavoriteArtworkRequest;
import com.example.gardenedennft.favoriteartwork.entity.FavoriteArtwork;
import com.example.gardenedennft.favoriteartwork.mapper.FavoriteArtWorkDTOMapper;
import com.example.gardenedennft.favoriteartwork.mapper.FavoriteListArtworkDTOMapper;
import com.example.gardenedennft.favoriteartwork.repo.FavoriteArtWorkRepo;
import com.example.gardenedennft.favoriteartwork.service.FavoriteArtWorkService;
import com.example.gardenedennft.owner.repo.OwnerRepo;
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

    private final ArtworkRepo artworkRepo;

    private final FavoriteListArtworkDTOMapper favoriteListArtworkDTOMapper;

    private final FavoriteArtWorkDTOMapper favoriteArtWorkDTOMapper;

    private final OwnerRepo ownerResponse;

    @Override
    public FavoriteArtWorkDTO addFavoriteArtWork(FavoriteArtworkRequest favoriteArtworkRequest) {
        String currentWalletAddress = favoriteArtworkRequest.getWalletAddress();
        Integer currentIdArtwork = favoriteArtworkRequest.getIdArtwork();

        FavoriteArtwork favoriteArtwork = findFavoriteArtWorkByWalletAddressAndIdArtwork(currentWalletAddress, currentIdArtwork);

        if (favoriteArtwork != null) {
            favoriteArtwork.setStatus(!favoriteArtwork.getStatus());
            favoriteArtWorkRepo.save(favoriteArtwork);
        } else {
            Artwork artwork = artworkRepo.findById(currentIdArtwork)
                    .orElseThrow(() -> new ResourceNotFoundException("Not found artwork with id " + currentIdArtwork));

            favoriteArtwork = favoriteArtWorkRepo.save(
                    FavoriteArtwork.builder()
                            .status(true)
                            .artwork(artwork)
                            .wallet_address(currentWalletAddress)
                            .build()
            );
        }

        FavoriteArtWorkDTO favoriteArtWorkDTO = favoriteArtWorkDTOMapper.apply(favoriteArtwork);

        return favoriteArtWorkDTO;
    }

    @Override
    public FavoriteArtwork findFavoriteArtWorkByWalletAddressAndIdArtwork(String walletAddress, Integer idArtwork) {
        Optional<FavoriteArtwork> favoriteArtwork = favoriteArtWorkRepo.findFavoriteArtWorkByWalletAddressAndIdArtwork(walletAddress, idArtwork);

        if(favoriteArtwork.isPresent()){
            return favoriteArtwork.get();
        }

        return null;
    }


    @Override
    public List<FavoriteArtWorkDTO> findListFavoriteArtworks(Integer idArtwork) {
        List<FavoriteArtwork> listFavoriteArtworks = favoriteArtWorkRepo.findListFavoriteArtworksByIdArtwork(idArtwork)
                .orElseThrow(() -> new ResourceNotFoundException("Not found favoriteArtwork with id artwork "+idArtwork));
        return favoriteListArtworkDTOMapper.apply(listFavoriteArtworks);

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
