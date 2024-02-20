package com.example.gardenedennft.TransactionType;


import java.util.UUID;

public interface TransactionTypeService {
    void addTransactionType(TransactionTypeRequest request);

    Boolean existsTransactionTypeByType(String type);

    TransactionType getTransactionTypeByType(String type);

    TransactionType getTransactionTypeById(UUID id);

    TransactionRepo getAll();
}
