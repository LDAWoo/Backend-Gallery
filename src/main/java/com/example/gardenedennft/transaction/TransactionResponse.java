package com.example.gardenedennft.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TransactionResponse extends JpaRepository<Transaction, Integer> {

    @Query("""
    SELECT t FROM Transaction t
    WHERE t.artwork.id = ?1
    """)
    Optional<Transaction> findTransactionByArtworkId(Integer id);
}
