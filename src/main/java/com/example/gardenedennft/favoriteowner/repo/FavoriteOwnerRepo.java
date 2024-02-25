package com.example.gardenedennft.favoriteowner.repo;

import com.example.gardenedennft.favoriteowner.dto.FavoriteOwnerDTO;
import com.example.gardenedennft.favoriteowner.entity.FavoriteOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FavoriteOwnerRepo extends JpaRepository<FavoriteOwner, UUID> {
    @Query("""
    SELECT COUNT(f) > 0 FROM FavoriteOwner f WHERE f.wallet_address = ?1
    """)
    Boolean existsFavoriteOwnerByWallet_address(String Wallet);
    @Query("""
    SELECT f from FavoriteOwner f where f.wallet_address=?1 and f.owner.id=?2
    """)
    FavoriteOwner findFavoriteOwnerByWallet_addressAndOwnerId(String Wallet_address,UUID id_Owner);
    @Query("""
         select f from FavoriteOwner f where f.owner.id=?1
        """)
    Optional<List<FavoriteOwner>> findFavoriteOwnersByOwnerId(UUID id_Owner);


}
