package com.example.gardenedennft.TransactionType;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TransactionTypeResponse extends JpaRepository<TransactionType, UUID> {

    Boolean existsTransactionTypeByType(String type);
    Optional<TransactionType> findTransactionTypeByType(String type);
}
