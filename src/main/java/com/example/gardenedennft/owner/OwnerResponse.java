package com.example.gardenedennft.owner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OwnerResponse extends JpaRepository<Owner, UUID> {

    @Transactional
    @Query("""
    SELECT o FROM Owner o WHERE o.wallet_address = ?1 
    """)
    Optional<Owner> findOwnerByWalletAddress(String walletAddress);

    @Transactional
    @Query("""
    SELECT o FROM Owner o WHERE o.artist.id = ?1 
    """)
    Optional<Owner> findOwnerByIdArtist(UUID id);

    @Query("""
    SELECT o FROM Owner o WHERE o.artist.id = ?1 
    """)
    Optional<List<Owner>> findAllOwnerByIdArtwork(UUID id);


    @Transactional(readOnly = true)
    @Query("SELECT COUNT(o) > 0 FROM Owner o WHERE o.wallet_address = ?1")
    Boolean existsOwnerByWalletAddress(String walletAddress);
}
