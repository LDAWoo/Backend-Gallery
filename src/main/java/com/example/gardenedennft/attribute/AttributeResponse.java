package com.example.gardenedennft.attribute;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AttributeResponse extends JpaRepository<Attribute, UUID> {

    @Query("""
    SELECT a FROM Attribute a
    WHERE a.artwork.id = ?1
    """)
    Optional<List<Attribute>> findAllAttributeByIdArtwork(Integer id);
}
