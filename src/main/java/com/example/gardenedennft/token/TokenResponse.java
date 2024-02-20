package com.example.gardenedennft.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenResponse extends JpaRepository<Token, UUID> {

    @Query("""
        SELECT t FROM Token t INNER JOIN Owner o ON t.artist.id = o.id
        WHERE o.id = :id AND(t.expired = FALSE or t.revoked = FALSE)
    """)
    List<Token> findAllValidTokenByArtist(UUID id);

    Optional<Token> findByToken(String token);
}
