package com.example.gardenedennft.artwork.repo;

import com.example.gardenedennft.artwork.entity.Artwork;
import com.example.gardenedennft.artwork.entity.request.ArtworkConditionRequest;
import com.example.gardenedennft.owner.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArtworkRepo extends JpaRepository<Artwork,Integer> {
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
        SELECT DISTINCT 
        a 
        FROM 
            Artwork a 
        LEFT JOIN 
            Attribute att ON att.artwork.id = a.id
        WHERE 
            a.owner.artist.id = :#{#request.id}
        AND 
            a.status = :#{#request.status}
        AND
           (:#{#request.name} IS NULL OR a.name LIKE CONCAT('%', :#{#request.name}, '%'))
        OR
           (:#{#request.attributes} IS NULL OR att IN :#{#request.attributes})    
           
        """)
    Optional<List<Artwork>> findArtworkByIdArtistAndStatusAndCondition(@Param("request") ArtworkConditionRequest request);

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
    WHERE a.owner.artist.id = ?1
    AND a.status = ?2
    """)
    Optional<List<Artwork>> findArtworkByIdArtistAndStatus(UUID id, Integer status);

    @Query("""
    SELECT a FROM Artwork a
    LEFT JOIN a.owner o
    LEFT JOIN o.artist ar
    WHERE a.id = ?1
    AND ar.email = ?2
    """)
    Optional<Artwork> findArtworkByIdAndEmail(Integer id, String email);

    @Query("""
    SELECT a FROM Artwork a
    WHERE a.owner = ?1
    """)
    Optional<List<Artwork>> findArtworkByOwner(Owner owner);
}
