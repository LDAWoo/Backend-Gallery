package com.example.gardenedennft.artwork;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArtworkResponse extends JpaRepository<Artwork,Integer> {

    @Query("""
    SELECT a FROM Artwork a WHERE a.wallet_address = ?1
    """)
    Optional<List<Artwork>> findArtworkByWalletAddress(String walletAddress);

    @Query("""
    SELECT a FROM Artwork a 
    LEFT JOIN a.owner o
    LEFT JOIN o.artist ar
    WHERE ar.email = ?1
    AND a.status = ?2
    """)
    Optional<List<Artwork>> findArtworkByEmailAndStatus(String email, Integer status);

    @Query("""
    SELECT a FROM Artwork a 
    LEFT JOIN a.owner o
    LEFT JOIN o.artist ar
    WHERE ar.email = ?1
    AND a.status IS NOT NULL
    """)
    Optional<List<Artwork>> findArtworkByEmail(String email);

    @Query("""
    SELECT a FROM Artwork a 
    LEFT JOIN a.owner ow
    WHERE ow.id = ?1
    AND a.status = ?2
    """)
    Optional<List<Artwork>> findArtworkByIdOwnerAndStatus(UUID id, Integer status);

    @Query("""
    SELECT  a FROM Artwork a
    LEFT JOIN a.owner o
    LEFT JOIN o.artist ar
    WHERE a.id = ?1
    AND ar.email = ?2
    """)
    Optional<Artwork> findArtworkByIdAndEmail(Integer id, String email);
}