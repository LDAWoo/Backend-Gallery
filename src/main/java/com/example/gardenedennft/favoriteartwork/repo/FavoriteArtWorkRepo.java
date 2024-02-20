package com.example.gardenedennft.favoriteartwork.repo;

import com.example.gardenedennft.favoriteartwork.entity.FavoriteArtwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface FavoriteArtWorkRepo extends JpaRepository<FavoriteArtwork, UUID> {

    @Query("""
    SELECT COUNT(f) > 0 FROM FavoriteArtwork f WHERE f.wallet_address = ?1
    """)
    Boolean existsFavoriteArtworkByWallet_address(String wallet);

    @Query("""
    SELECT f FROM FavoriteArtwork f
    WHERE f.wallet_address = ?1
    AND f.artwork.id = ?2
    """)
    Optional<FavoriteArtwork> findFavoriteArtWorkByWalletAddressAndIdArtwork(String walletAddress, Integer idArtwork);

    @Query("""
    SELECT f FROM FavoriteArtwork f
    WHERE f.artwork.id = ?1
    """)
    Optional<List<FavoriteArtwork>> findListFavoriteArtworksByIdArtwork(Integer id);

}
