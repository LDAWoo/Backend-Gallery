package com.example.gardenedennft.favoriteartist.repo;

import com.example.gardenedennft.favoriteartist.entity.FavoriteArtist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FavoriteArtistRepo extends JpaRepository<FavoriteArtist, UUID> {
    @Query("""
    SELECT COUNT(f) > 0 FROM FavoriteArtist f WHERE f.wallet_address = ?1
    """)
    Boolean existsFavoriteArtistByWalletAddress(String Wallet);
    @Query("""
    SELECT f FROM FavoriteArtist f where f.wallet_address=?1 and f.artist.id=?2
    """)
    FavoriteArtist findFavoriteArtistByWalletAddressAndArtistId(String Wallet_address, UUID id);
    @Query("""
         SELECT f FROM FavoriteArtist f where f.artist.id=?1
    """)
    Optional<List<FavoriteArtist>> findFavoriteArtistByArtistId(UUID id);


}
