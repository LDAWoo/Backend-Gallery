package com.example.gardenedennft.artist;

import com.example.gardenedennft.owner.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface ArtistResponse extends JpaRepository<Artist, UUID> {
    Optional<Artist> findArtistByEmail(String email);

    Boolean existsArtistByEmail(String email);

    Optional<Artist> findOwnerByEmailAndStatus(String email, Boolean status);

    Optional<Artist> findArtistBySymbol(String symbol);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("""
                UPDATE Artist a
                SET a.status = true
                WHERE a.email = ?1
            """)
    void updateStatusByEmail(String email);
}
