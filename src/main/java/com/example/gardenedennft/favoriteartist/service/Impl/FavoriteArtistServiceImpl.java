package com.example.gardenedennft.favoriteartist.service.Impl;

import com.example.gardenedennft.artist.entity.Artist;
import com.example.gardenedennft.artist.repo.ArtistRepo;
import com.example.gardenedennft.exception.ResourceNotFoundException;
import com.example.gardenedennft.favoriteartist.dto.FavoriteArtistDTO;
import com.example.gardenedennft.favoriteartist.dto.request.FavoriteArtistRequest;
import com.example.gardenedennft.favoriteartist.entity.FavoriteArtist;
import com.example.gardenedennft.favoriteartist.mapper.FavoriteListArtistDTOMapper;
import com.example.gardenedennft.favoriteartist.mapper.FavoriteArtistDTOMapper;
import com.example.gardenedennft.favoriteartist.repo.FavoriteArtistRepo;
import com.example.gardenedennft.favoriteartist.service.FavoriteArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteArtistServiceImpl implements FavoriteArtistService {
    private final FavoriteArtistRepo favoriteArtistRepo;

    private final FavoriteArtistDTOMapper favoriteArtistDTOMapper;

    private final FavoriteListArtistDTOMapper favoriteListArtistDTOMapper;

    private final ArtistRepo artistResponse;
    @Override
    public FavoriteArtistDTO addFavoriteArtist(FavoriteArtistRequest favoriteArtistRequest) {
        UUID currentId = favoriteArtistRequest.getId_artist();
        String walletAddress = favoriteArtistRequest.getWallet_address();

        FavoriteArtist favoriteArtist = favoriteArtistRepo.findFavoriteArtistByWalletAddressAndArtistId(walletAddress, currentId);

        if (favoriteArtist != null) {
            favoriteArtist.setStatus(!favoriteArtist.getStatus());
            return favoriteArtistDTOMapper.apply(favoriteArtistRepo.save(favoriteArtist));
        } else {
            Artist artist = artistResponse.findById(currentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Artist not found with id " + currentId));
            favoriteArtist = FavoriteArtist.builder()
                    .wallet_address(walletAddress)
                    .status(true)
                    .artist(artist)
                    .build();
            return favoriteArtistDTOMapper.apply(favoriteArtistRepo.save(favoriteArtist));
        }
    }

    @Override
    public List<FavoriteArtistDTO> findListFavoriteArtists(UUID id) {
        List<FavoriteArtist> favoriteArtists= favoriteArtistRepo.findFavoriteArtistByArtistId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found favoriteArtist with id artist "+id));

        return favoriteListArtistDTOMapper.apply(favoriteArtists);
    }

    @Override
    public Boolean existsArtistById(UUID id) {
        return favoriteArtistRepo.existsById(id);
    }

    @Override
    public Boolean existsFavoriteArtistByWalletAddress(String walletAddress) {
        return favoriteArtistRepo.existsFavoriteArtistByWalletAddress(walletAddress);
    }
}
