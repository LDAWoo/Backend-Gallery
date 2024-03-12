package com.example.gardenedennft.artist;

import com.example.gardenedennft.artist.entity.request.ArtistConditionRequest;
import com.example.gardenedennft.owner.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArtistResponse extends JpaRepository<Artist, UUID> {
    Optional<Artist> findArtistByEmail(String email);

    Boolean existsArtistByEmail(String email);

    Optional<Artist> findOwnerByEmailAndStatus(String email, Boolean status);

    Optional<Artist> findArtistBySymbol(String symbol);


    @Query(value = """
        SELECT DISTINCT 
        ar.id,
        ar.name,
        ar.email,
        ar.symbol,
        ar.twitter_url,
        ar.website_url,
        ar.telegram_url,
        ar.image_url,
        ar.discord_url,
        ar.bio
        FROM
            _artist ar
        WHERE 
            ar.status = 1
            AND
            ar.name LIKE CONCAT('%', :#{#request.name}, '%') OR ar.symbol LIKE CONCAT('%', :#{#request.symbol}, '%')
        """, nativeQuery = true)
    Optional<List<ArtistSqlNativeResult>> findAllArtistByCondition(@Param("request") ArtistConditionRequest request);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("""
                UPDATE Artist a
                SET a.status = true
                WHERE a.email = ?1
            """)
    void updateStatusByEmail(String email);
}
