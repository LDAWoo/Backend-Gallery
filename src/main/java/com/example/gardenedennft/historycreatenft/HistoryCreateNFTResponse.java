package com.example.gardenedennft.historycreatenft;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HistoryCreateNFTResponse extends JpaRepository<HistoryCreateNFT, UUID> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("""
        SELECT h FROM HistoryCreateNFT h WHERE h.id = :id AND h.status = :status 
    """)
    HistoryCreateNFT findStatusHistoryCreateNFTById(UUID id,Integer status);

    Optional<List<HistoryCreateNFT>> findHistoryCreateNFTByEmail(String email);

    @Query("""
    SELECT h FROM HistoryCreateNFT h WHERE h.email = :email AND h.status = :status 
    """)
    Optional<List<HistoryCreateNFT>> findHistoryCreateNFTByEmailAndStatusNot(String email, Integer status);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE HistoryCreateNFT h 
        SET h.status = 1 
        WHERE h.id = ?1
    """)
    void updateStatusHistoryCreateNFTById(UUID id);
}
