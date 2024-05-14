package com.example.gardenedennft.marketplace.repo;

import com.example.gardenedennft.marketplace.entity.Marketplace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface MarketplaceRepo extends JpaRepository<Marketplace, UUID> {


    @Query("""
    SELECT COUNT(m) > 0 FROM Marketplace m WHERE m.creatorAddress = ?1
    """)
    Boolean existsMarketplaceByCreateAddress(String createAddress);

    @Query("""
    SELECT m FROM Marketplace m WHERE m.creatorAddress = ?1
    """)
    Optional<Marketplace> findMarketplaceByCreatorAddress(String creatorAddress);
}
