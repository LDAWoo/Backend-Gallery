package com.example.gardenedennft.attribute.repo;

import com.example.gardenedennft.attribute.entity.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AttributeRepo extends JpaRepository<Attribute, UUID> {

    @Query("""
    SELECT a FROM Attribute a
    WHERE a.artwork.id = ?1
    """)
    Optional<List<Attribute>> findAllAttributeByIdArtwork(Integer id);

    @Query("""
    SELECT a FROM Attribute a
    WHERE a.trait_type = ?1 AND a.value = ?2
    """)
    List<Attribute> findAllAttributeByTraitAndValue(String trait, String value);
}
